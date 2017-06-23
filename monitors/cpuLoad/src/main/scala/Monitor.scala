import java.util.Date

import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients
import org.apache.http.{HttpHeaders, HttpStatus}
import org.cmsfs.lib.api.config.{ConfigApi, ConnectApi}
import org.cmsfs.lib.execute.ssh.ExecuteSsh
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.{Format, JsPath, Json, Reads}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object Monitor extends App {
  def getServers(): Seq[ConfigServer] = {
    try {
      Json.parse(ConfigApi.getServersByMonitor("cpuLoad")).as[List[ConfigServer]]
    } catch {
      case ex: Exception => {
        println("get config server error", ex.getMessage)
        List.empty
      }
    }
  }

  def getConnect(server: String): Option[SshConnect] = {
    try {
      println(s"get connect ${server}")
      val req = ConnectApi.getSshConnectByName(server)
      Some(Json.parse(req).as[SshConnect])
    } catch {
      case ex: Exception => {
        println("get connect error", ex.getMessage)
        None
      }
    }
  }

  def start() = {
    while (true) {
      val utc = new Date().toInstant.toString
      println(utc)
      for (server <- getServers()) {
        println(s"collect server: ${server}")
        val future: Future[Unit] = Future {
          for (c <- getConnect(server.name)) {
            val resOpt = ExecuteSsh.ssh("~/.ssh/id_rsa", c.ip, c.port, c.user, c.password, "cat /proc/loadavg")
            print(resOpt)
            for (rs <- resOpt) {
              val loadArry = Json.parse(rs).as[List[String]].head.trim.split("\\s")
              val content: String = Json.toJson(EsCupLoad(server.name, "cpuLoad", utc, loadArry(0).toDouble, loadArry(1).toDouble, loadArry(2).toDouble)).toString()
              println(content)
              sendES(content)
            }
          }
        }

        future onComplete {
          case Success(_) =>
          case Failure(ex) => println(ex.getMessage)
        }
      }
      Thread.sleep(1000 * 60)
    }
  }

  def sendES(content: String): Unit = {
    val httpClient = HttpClients.createDefault
    val post = new HttpPost("http://elasticsearch.cmsfs.org:9200/monitor/os")
    val s = new StringEntity(content);
    s.setContentEncoding("UTF-8");
    s.setContentType("application/json");
    post.setHeader(HttpHeaders.AUTHORIZATION, "Basic ZWxhc3RpYzpjaGFuZ2VtZQ==")
    post.setEntity(s)
    val response = httpClient.execute(post)
    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
      println("es send succcess.")
    } else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
      println("es send succcess.")
    } else {
      println("es send error: ", response.getEntity)
    }
  }


  def test(conn: SshConnect, command: String, ts: ((SshConnect, String) => Unit)*): Unit = {

  }

  start()
}

case class SshConnect(name: String, ip: String, port: Int, user: String, password: Option[String], private_key: Option[String])

object SshConnect {
  implicit val residentReads: Reads[SshConnect] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "ip").read[String] and
      (JsPath \ "port").read[Int] and
      (JsPath \ "user").read[String] and
      (JsPath \ "password").readNullable[String] and
      (JsPath \ "private_key").readNullable[String]
    ) (SshConnect.apply _)
}

case class ConfigServer(name: String)

object ConfigServer {
  implicit val format: Format[ConfigServer] = Json.format
}

case class EsCupLoad(`@host`: String, `@metric`: String, `@timestamp`: String, `l1m`: Double, `l5m`: Double, `l15m`: Double)

object EsCupLoad {
  implicit val format: Format[EsCupLoad] = Json.format
}
