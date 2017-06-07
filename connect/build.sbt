organization := "org.cmsfs"

name := "connect"

version := "1.0"

scalaVersion in ThisBuild := "2.12.2"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.0.7",
  "com.typesafe.slick" %% "slick" % "3.2.0",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.0",
  "mysql" % "mysql-connector-java" % "6.0.6"
)