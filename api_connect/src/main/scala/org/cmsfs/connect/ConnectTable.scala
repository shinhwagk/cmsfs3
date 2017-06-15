package org.cmsfs.connect

object ConnectTable {

  case class ConnectJdbc(name: String, kind: String, port: Int, protocol: String, service: String, user: String, password: String)

  case class ConnectSsh(name: String, port: Int, user: String, password: Option[String], privateKey: Option[String])

}