import java.util.Date

import akka.actor.ActorSystem
import akka.kafka.ProducerSettings
import akka.stream.scaladsl.{Broadcast, Flow, GraphDSL, Sink, Source}
import akka.stream.{ActorMaterializer, SinkShape}
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{ByteArraySerializer, StringSerializer}
import org.cmsfs.lib.api.config.{ConfigApi, ConnectApi}
import org.quartz.CronExpression
import play.api.libs.json.Json

import scala.concurrent.duration._

object Boot extends App {
  implicit val system = ActorSystem("monitor")
  implicit val materializer = ActorMaterializer()

  val boot = Source.tick(0.seconds, 1.seconds, ())
    .map(_ => ConfigApi.getCronServersByMonitor("tablespaceSpace"))
    .mapConcat(Json.parse(_).as[List[Server]])
    .filter(server => new CronExpression(server.cron).isSatisfiedBy(new Date()))

  val processCollect = Flow[Server].map { server =>
    ConnectApi.get(server.name)
//    ConfigApi.getProcessConfigByMonitorAndProcess("tablespaceSpace", "collect")

    ""
  }

  val processCollectFormat = Flow[String].map(Format.formatLine)


  val processAlarmPhone = Flow[FileSystem].map { fs =>
    ConfigApi.getProcessConfigByMonitorAndProcess("tablespaceSpace", "alarm")

    ""
  }

  val processAnalyze = Flow[FileSystem].map { fs =>

    ""
  }

  val producerSettings = ProducerSettings(system, new ByteArraySerializer, new StringSerializer)
    .withBootstrapServers("localhost:9092")

  val ton = Flow[String].map(x => new ProducerRecord[Array[Byte], String]("topic1", x))

  val process =
    Sink.fromGraph(GraphDSL.create(processCollect, processCollectFormat, processAnalyze, processAlarmPhone, ton)((_, _, _, _, _)) { implicit b =>
      (pct, fmt, pae, pam, to) =>

        import GraphDSL.Implicits._

        val server = b.add(Flow[Server])

        val format = b.add(Broadcast[FileSystem](2))

        server ~> pct ~> fmt ~> format

        format ~> pae ~> to ~> Sink.ignore

        format ~> pam ~> to ~> Sink.ignore

        SinkShape(server.in)
    })

  boot.runWith(process)

}
