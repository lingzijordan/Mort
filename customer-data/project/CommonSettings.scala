import sbt._
import sbt.Keys._
import sbt.Resolver

object CommonSettings {
  //The akka version should be the same as spray dependency
  val akkaVersion = "2.3.4"

  val scalaTestVersion = "2.2.5"

  val akka = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  val akkaTestKit = "com.typesafe.akka" %% "akka-testkit" % akkaVersion
  val scalaTest = "org.scalatest" %% "scalatest" % scalaTestVersion % "test"


  lazy val commonSettings = Seq(
    name := "db-customer-data",
    organization := "com.bigeyedata",
    version := "0.1",
    scalaVersion := "2.10.5",
    scalacOptions in Test ++= Seq("-Yrangepos"),
    resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo),
    libraryDependencies ++= Seq(akka, akkaTestKit, scalaTest)
  )
}