import sbt.Keys._
import sbt.{Resolver, _}
import Dependencies._

object CommonSettings {
  val akka = Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test"
  )

  val scalaTest = Seq(
    "org.scalatest" %% "scalatest" % "2.2.1" % "test" withSources() withJavadoc(),
    "org.scalacheck" %% "scalacheck" % "1.12.1" % "test" withSources() withJavadoc()
  )

  lazy val commonSettings = Seq(
    name := "mort",
    organization := "com.bigeyedata",
    version := "0.1",
    scalaVersion := "2.10.5",
    scalacOptions in Test ++= Seq("-Yrangepos"),
    resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo),
    libraryDependencies ++= akka,
    libraryDependencies ++= scalaTest
  )
}