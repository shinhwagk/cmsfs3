package org.cmsfs.connect

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.io.StdIn

object ConnectBootstrap extends App {

  val (host, port) = ("localhost", 8082)

  implicit val system = ActorSystem("cmsfs")
  implicit val materializer = ActorMaterializer()

  implicit val ece: ExecutionContextExecutor = system.dispatcher

  val bindingFuture: Future[Http.ServerBinding] = Http().bindAndHandle(ConnectRoutes.route, host, port)

  println(s"Server online at http://${host}:${port}/\nPress RETURN to stop...")
  StdIn.readLine()
  bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())
}
