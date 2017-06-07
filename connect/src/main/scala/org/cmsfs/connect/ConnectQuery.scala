package org.cmsfs.connect

import org.cmsfs.connect.ConnectTable.{ConnectJdbc, ConnectSsh}
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

trait ConnectQuery {
  private val db = Database.forConfig("cmsfs")

  def getConnectJdbcByKindAndName(kind: String, name: String): Future[ConnectJdbc] = {
    db.run(sql"select * from connect_jdbc where kind = ${kind} and name = ${name}".as[ConnectJdbc].head)
  }

  def getConnectSsh(name: String): Future[ConnectSsh] = {
    db.run(sql"select connects from connect_ssh where name = ${name}".as[ConnectSsh].head)
  }

  def getConnectJdbcOracle(name: String): Future[ConnectJdbc] = {
    getConnectJdbcByKindAndName("oracle", name)
  }

  def getConnectJdbcMysql(name: String): Future[ConnectJdbc] = {
    getConnectJdbcByKindAndName("mysql", name)
  }

  def getGroupJdbcOracleByName(name: String): Future[Vector[String]] = {
    db.run(sql"select connects from connect_group_jdbc_oracle where name = ${name}".as[Vector[String]].head)
  }

  def getGroupJdbcMysqlByName(name: String): Future[List[String]] = {
    db.run(sql"select connects from connect_group_jdbc_mysql where name = ${name}".as[List[String]].head)
  }

  def getGroupSshByName(name: String): Future[List[String]] = {
    db.run(sql"select connects from connect_group_ssh where name = ${name}".as[List[String]].head)
  }
}
