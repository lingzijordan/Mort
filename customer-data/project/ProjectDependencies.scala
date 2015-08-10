import sbt._
import CommonSettings._

object ProjectDependencies extends Build {

  lazy val common = Project(id = "common", base = file("common"))
    .settings(commonSettings: _*)

  lazy val dataWriter = Project(id = "dataWriter", base = file("data-writer"))
    .settings(commonSettings: _*)
    .dependsOn(common)

  lazy val dbDataEngine = Project(id = "dbDataEngine", base = file("db-data-engine"))
    .settings(commonSettings: _*)
    .dependsOn(dataWriter)
    .dependsOn(common)

  lazy val dbDomainService = Project(id = "dbDomainService", base = file("db-domain-service"))
    .settings(commonSettings: _*)
    .dependsOn(dbDataEngine)

  lazy val root = Project(id = "customer-data", base = file("."))
    .aggregate(
      common,
      dataWriter,
      dbDataEngine,
      dbDomainService
    )
}