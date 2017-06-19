package org.cmsfs.connect

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

import scala.util.{Failure, Success}

object ConnectRoutes extends ConnectQuery with ConnectJson {
  val route: Route =
    pathPrefix("v1" / "connect") {
      get {
        jdbcRoute ~ sshRoute
      } ~ pathPrefix("group") {
        groupRoute
      }
    }

  /**
    * path: get /v1/connect/group/jdbc/oracle/:name
    * path: get /v1/connect/group/jdbc/mysql/:name
    */
  val groupRoute: Route = pathPrefix("jdbc") {
    pathPrefix("oracle" / Segment) { groupName =>
      onComplete(getGroupJdbcOracleByName(groupName)) {
        case Success(names) => complete(names)
        case Failure(ex) => complete("aaa")
      } ~ pathPrefix("mysql" / Segment) { groupName =>
        onComplete(getGroupJdbcMysqlByName(groupName)) {
          case Success(names) => complete(names)
          case Failure(ex) => complete((InternalServerError, "That wasn't valid! "))
        }
      }
    }
  }

  /**
    * path get /v1/connect/jdbc/oracle/:name
    * path get /v1/connect/jdbc/mysql/:name
    */
  val jdbcRoute: Route = pathPrefix("jdbc") {
    jdbcMysqlRoute ~ jdbcOracleRoute
  }

  val jdbcMysqlRoute: Route = path("mysql" / Segment) { name =>
    onComplete(getConnectJdbcMysql(name)) {
      case Success(names) => complete(names)
      case Failure(ex) => complete("aaa")
    }
  }

  val jdbcOracleRoute: Route = path("oracle" / Segment) { name =>
    onComplete(getConnectJdbcOracle(name)) {
      case Success(names) => complete(names)
      case Failure(ex) => complete("aaa")
    }
  }

  /**
    * path get /v1/connect/ssh/:name
    */
  val sshRoute: Route = path("ssh" / Segment) { name =>
    entity(as[String]) { exclude =>
      onComplete(getConnectSsh(name)) {
        case Success(group) => complete("xx")
        case Failure(ex) => complete("aaa")
      }
    }
  }
}