package org.cmsfs.execute.script

import java.io.File
import java.net.URL
import java.nio.charset.Charset
import java.util.concurrent.ThreadLocalRandom

import org.apache.commons.io.FileUtils
import play.api.libs.json.JsValue

object ScriptExecute {

  private def createWorkDirAndDeleteAfterExecute(): (String, () => Unit) = {
    val dirName = s"workspace/${ThreadLocalRandom.current.nextLong(100000000).toString}"
    val dir = new File(dirName)
    FileUtils.forceMkdir(dir)

    (dirName, () => FileUtils.deleteDirectory(dir))
  }

  private def executorChoice(mainFile: String): Seq[String] = {
    val name = mainFile.split("\\.").last
    name match {
      case "py" => Seq("python3")
      case "sh" => Seq("sh")
      case "ps1" => Seq("powershell")
      case _ => throw new Exception(s"actuator unknown: ${name}")
    }
  }

  private def downScript(url: String, dirPath: String): String = {
    val scriptName = url.split("/").last
    FileUtils.copyURLToFile(new URL(url), new File(dirPath + "/" + scriptName))
    dirPath + "/" + scriptName
  }

  private def writeFile(data: String, dirPath: String, fileName: String): Unit =
    writeFile(s"${dirPath}/${fileName}", data)

  private def writeFile(fileName: String, content: String): Unit = {
    FileUtils.writeStringToFile(new File(fileName), content, Charset.forName("UTF-8"), false)
  }

  def executeScript(files: Seq[Seq[String]], env: Map[String, String], dataOpt: Option[String], argsOpt: Option[JsValue]): Option[String] = {
    import sys.process._

    val mainFile = files(0).last

    var executor: Seq[String] = executorChoice(mainFile)
    val (workDirName, deleteWorkDirFun) = createWorkDirAndDeleteAfterExecute()

    files.foreach(file => downScript(file.mkString("/"), workDirName))

    executor = executor :+ mainFile

    if (dataOpt.isDefined) {
      writeFile(dataOpt.get, workDirName, "data.json")
      executor = executor :+ "data.json"
    }

    if (argsOpt.isDefined) {
      writeFile(argsOpt.get.toString(), workDirName, "args.json")
      executor = executor :+ "args.json"
    }

    val result: Option[String] = try {
      Some(Process(executor, new java.io.File(workDirName), env.toSeq: _*).!!)
    } catch {
      case ex: Exception =>
        println(ex.getMessage)
        None
    }
    deleteWorkDirFun()
    result
  }

}
