object Format {
  def formatLine(result: String): FileSystem = {
    val row = result.split("\\s+")
    FileSystem(row(0), row(1).toInt, row(2), row(3), row(4).dropRight(1), row(5))
  }

}

case class FileSystem(Filesystem: String, Size: Int, Used: String, Avail: String, `Ued%`: String, `Mounted on`: String)

case class Server(name: String, cron: String)

case class ConfigAlarm()

case class ProcessCollect(result: String, env: Map[String, String])