package org.monitor.cmsfs.kafka

import java.io.{File, IOException}
import java.nio.file.{FileVisitOption, Files, Paths}
import java.util.{Comparator, Properties}
import java.util.concurrent.atomic.AtomicReference
import javax.management.InstanceNotFoundException

import kafka.server.KafkaServerStartable
import org.apache.curator.test.TestingServer
import org.monitor.cmsfs.util.PropertiesLoader
import org.slf4j.LoggerFactory

import scala.collection.JavaConverters._

class KafkaLocalServer private (kafkaProperties: Properties, zooKeeperServer: KafkaLocalServer.ZooKeeperLocalServer) {

  private val kafkaServerRef = new AtomicReference[KafkaServerStartable](null)

  def start(): Unit = {
    if (kafkaServerRef.get == null) {
      val newKafkaServer = KafkaServerStartable.fromProps(kafkaProperties)
      if (kafkaServerRef.compareAndSet(null, newKafkaServer)) {
        zooKeeperServer.start()
        val kafkaServer = kafkaServerRef.get()
        kafkaServer.startup()
      } else newKafkaServer.shutdown()
    }
  }

  private[kafka] def restart(): Unit = {
    val kafkaServer = kafkaServerRef.get()
    if (kafkaServer != null) {
      kafkaServer.shutdown()
      kafkaServer.startup()
    }
  }

  def stop(): Unit = {
    val kafkaServer = kafkaServerRef.getAndSet(null)
    if (kafkaServer != null) {
      try kafkaServer.shutdown()
      catch {
        case _: Throwable => ()
      }
      try zooKeeperServer.stop()
      catch {
        case _: InstanceNotFoundException => ()
      }
    }
  }

}

object KafkaLocalServer {
  final val DefaultPort = 9092
  final val DefaultPropertiesFile = "/kafka-server.properties"
  final val DefaultResetOnStart = true

  private final val KafkaDataFolderName = "kafka_data"

  private val Log = LoggerFactory.getLogger(classOf[KafkaLocalServer])

  private lazy val tempDir = System.getProperty("java.io.tmpdir")

  def apply(cleanOnStart: Boolean): KafkaLocalServer = this(DefaultPort, ZooKeeperLocalServer.DefaultPort, DefaultPropertiesFile, Some(tempDir), cleanOnStart)

  def apply(kafkaPort: Int, zookeeperServerPort: Int, kafkaPropertiesFile: String, targetDir: Option[String], cleanOnStart: Boolean): KafkaLocalServer = {
    val kafkaDataDir = dataDirectory(targetDir, KafkaDataFolderName)
    Log.info(s"Kafka data directory is $kafkaDataDir.")

    val kafkaProperties = createKafkaProperties(kafkaPropertiesFile, kafkaPort, zookeeperServerPort, kafkaDataDir)

    if (cleanOnStart) deleteDirectory(kafkaDataDir)

    new KafkaLocalServer(kafkaProperties, new ZooKeeperLocalServer(zookeeperServerPort, cleanOnStart, targetDir))
  }

  private def createKafkaProperties(kafkaPropertiesFile: String, kafkaPort: Int, zookeeperServerPort: Int, dataDir: File): Properties = {
    val kafkaProperties = PropertiesLoader.from(kafkaPropertiesFile)
    kafkaProperties.setProperty("log.dirs", dataDir.getAbsolutePath)
    kafkaProperties.setProperty("listeners", s"PLAINTEXT://:$kafkaPort")
    kafkaProperties.setProperty("zookeeper.connect", s"localhost:$zookeeperServerPort")
    kafkaProperties
  }

  private def deleteDirectory(directory: File): Unit = {
    if (directory.exists()) try {
      val rootPath = Paths.get(directory.getAbsolutePath)

      val files = Files.walk(rootPath, FileVisitOption.FOLLOW_LINKS).sorted(Comparator.reverseOrder()).iterator().asScala
      files.foreach(Files.delete)
      Log.debug(s"Deleted ${directory.getAbsolutePath}.")
    } catch {
      case e: Exception => Log.warn(s"Failed to delete directory ${directory.getAbsolutePath}.", e)
    }
  }

  private def dataDirectory(baseDirPath: Option[String], directoryName: String): File = {
    lazy val tempDirMessage = s"Will attempt to create folder $directoryName in the system temporary directory: $tempDir"

    val maybeBaseDir = baseDirPath.map(new File(_)).filter(f => f.exists())

    val baseDir = {
      maybeBaseDir match {
        case None =>
          Log.warn(s"Directory $baseDirPath doesn't exist. $tempDirMessage.")
          new File(tempDir)
        case Some(directory) =>
          if (!directory.isDirectory()) {
            Log.warn(s"$baseDirPath is not a directory. $tempDirMessage.")
            new File(tempDir)
          } else if (!directory.canWrite()) {
            Log.warn(s"The application does not have write access to directory $baseDirPath. $tempDirMessage.")
            new File(tempDir)
          } else directory
      }
    }

    val dataDirectory = new File(baseDir, directoryName)
    if (dataDirectory.exists() && !dataDirectory.isDirectory())
      throw new IllegalArgumentException(s"Cannot use $directoryName as a directory name because a file with that name already exists in $dataDirectory.")

    dataDirectory
  }

  private class ZooKeeperLocalServer(port: Int, cleanOnStart: Boolean, targetDir: Option[String]) {
    private val zooKeeperServerRef = new AtomicReference[TestingServer](null)

    def start(): Unit = {
      val zookeeperDataDir = dataDirectory(targetDir, ZooKeeperLocalServer.ZookeeperDataFolderName)
      if (zooKeeperServerRef.compareAndSet(null, new TestingServer(port, zookeeperDataDir, /*start=*/ false))) {
        Log.info(s"Zookeeper data directory is $zookeeperDataDir.")

        if (cleanOnStart) deleteDirectory(zookeeperDataDir)

        val zooKeeperServer = zooKeeperServerRef.get
        zooKeeperServer.start() // blocking operation
      }
      // else it's already running
    }

    def stop(): Unit = {
      val zooKeeperServer = zooKeeperServerRef.getAndSet(null)
      if (zooKeeperServer != null)
        try zooKeeperServer.stop()
        catch {
          case _: IOException => ()
        }
    }
  }

  object ZooKeeperLocalServer {
    private[kafka] final val DefaultPort = 2181
    private final val ZookeeperDataFolderName = "zookeeper_data"
  }
}

