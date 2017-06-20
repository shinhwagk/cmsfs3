case class Server(name: String, cron: String)

case class ConfigAlarm()

case class ProcessCollect(result: String, env: Map[String, String])