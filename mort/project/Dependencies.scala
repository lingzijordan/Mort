import sbt._

object Dependencies {
  //The akka version should be the same as spray dependency
  val akkaVersion = "2.3.4"
  val specs2Version = "2.4"
  val scalikejdbcVersion = "2.2.7"
  val skinnyValidatorVersion = "1.3.19"
  val sprayVersion = "1.3.2"
  val json4sVersion = "3.2.11"
  val logbackVersion = "1.0.13"
  val sparkVersion = "1.4.0"

  val mysqlDriverVersion = "5.1.35"

  val scalikejdbc = Seq(
    "org.scalikejdbc" %% "scalikejdbc" % scalikejdbcVersion,
    "org.scalikejdbc" %% "scalikejdbc-config" % scalikejdbcVersion,
    "org.scalikejdbc" %% "scalikejdbc-test" % scalikejdbcVersion % "test"
  )
  val skinnyValidator = "org.skinny-framework" %% "skinny-validator" % skinnyValidatorVersion

  val sprayHttpx = "io.spray" %% "spray-httpx" % sprayVersion

  val json4s = "org.json4s" %% "json4s-native" % json4sVersion

  val json4sExt = "org.json4s"  %% "json4s-ext"   % json4sVersion

  val spray = Seq(
    "io.spray" %% "spray-routing" % sprayVersion,
    "io.spray" %% "spray-can" % sprayVersion,
    "io.spray" %% "spray-servlet" % sprayVersion,
    "io.spray" %% "spray-json" % sprayVersion,
    "io.spray" %% "spray-testkit" % sprayVersion % "test"
  )

  val spark = Seq(
    "org.apache.spark" %% "spark-core" % sparkVersion,
    "org.apache.spark" %% "spark-sql" % sparkVersion exclude("org.scalamacros", "quasiquotes_2.10")
  )

  val logback = "ch.qos.logback" % "logback-classic" % logbackVersion

  val mysql = "mysql" % "mysql-connector-java" % mysqlDriverVersion
}
