package org.cmsfs.connect

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import org.cmsfs.connect.ConnectTable.{ConnectJdbc, ConnectSsh}
import spray.json.DefaultJsonProtocol

trait ConnectJson extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val connectSshFormat = jsonFormat5(ConnectSsh)
  implicit val connectJdbcFormat = jsonFormat7(ConnectJdbc)
}