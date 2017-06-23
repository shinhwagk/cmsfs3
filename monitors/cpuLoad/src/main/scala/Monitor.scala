import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients
import org.apache.http.{HttpHeaders, HttpStatus}
import org.cmsfs.lib.api.config.{ConfigApi, ConnectApi}
import org.cmsfs.lib.execute.ssh.ExecuteSsh
import play.api.libs.json.Json

import scala.concurrent.Future
import scala.util.{Failure, Success}

object Monitor {
  def getServers(): Seq[String] = {
    try {
      Json.parse(ConfigApi.getServersByMonitor("cpuLoad")).as[List[String]]
    } catch {
      case ex: Exception => List.empty
    }
  }

  def getConnect(server: String): Option[SshConnect] = {
    try {
      Some(Json.parse(ConnectApi.getSshConnectByName(server)).as[SshConnect])
    } catch {
      case ex: Exception => None
    }
  }

  def start() = {
    while (true) {
      for (server <- getServers()) {
        val future: Future[Unit] = Future {
          for (c <- getConnect(server)) {
            val resOpt = ExecuteSsh.ssh("~/.ssh/id_rsa", c.ip, c.port, c.user, c.password, "cat /proc/loadavg")
            for (rs <- resOpt) {
              val loadArry = rs.trim.split("\\s")
              val content = Json.toJson(Map("1m" -> loadArry(0), "5m" -> loadArry(1), "15m" -> loadArry(2))).toString()
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

  def sendES(content: String) = {
    val httpClient = HttpClients.createDefault
    val post = new HttpPost("http://elasticsearch.cmsfs.org:3000/monitor/os")
    val s = new StringEntity(content);
    s.setContentEncoding("UTF-8");
    s.setContentType("application/json");
    post.setHeader(HttpHeaders.AUTHORIZATION, "Basic ZWxhc3RpYzpjaGFuZ2VtZQ==")
    post.setEntity(s)
    val response = httpClient.execute(post)
    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

    }
  }
}

case class SshConnect(name: String, ip: String, port: Int, user: String, password: Option[String], privateKey: Option[String])