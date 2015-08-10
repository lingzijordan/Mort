import CommonSettings._
import sbt._

object ProjectDependencies extends Build {

  lazy val commons = project.in(file("commons"))
    .settings(commonSettings: _*)

  lazy val models = project.in(file("models"))
    .settings(commonSettings: _*)
    .dependsOn(commons)

  lazy val messages = project.in(file("messages"))
    .settings(commonSettings: _*)
    .dependsOn(commons)
    .dependsOn(models)

  lazy val persistance = project.in(file("persistance"))
    .settings(commonSettings: _*)
    .dependsOn(commons)
    .dependsOn(messages)
    .dependsOn(models)


  lazy val dataSet = Project(id = "dataSet", base = file("data-set"))
    .settings(commonSettings: _*)
    .dependsOn(persistance)
    .dependsOn(commons)
    .dependsOn(messages)
    .dependsOn(models)

  lazy val dbDataSet = Project(id = "dbDataSet", base = file("db-data-set"))
    .settings(commonSettings: _*)
    .dependsOn(persistance)
    .dependsOn(dataSet)
    .dependsOn(commons)
    .dependsOn(messages)
    .dependsOn(models)
    .dependsOn(cdataAgent)


  lazy val dataSource = Project(id = "dataSource", base = file("data-source"))
    .settings(commonSettings: _*)
    .dependsOn(commons)
    .dependsOn(persistance)
    .dependsOn(messages)
    .dependsOn(models)


  lazy val dbDataSource = Project(id = "dbDataSource", base = file("db-data-source"))
    .settings(commonSettings: _*)
    .dependsOn(persistance)
    .dependsOn(dataSource)
    .dependsOn(commons)
    .dependsOn(messages)
    .dependsOn(models)

  lazy val cdataAgent = Project(id = "cdataAgent", base = file("customer-data-agent"))
    .settings(commonSettings: _*)
    .dependsOn(commons)
    .dependsOn(messages)
    .dependsOn(models)

  lazy val report  = Project(id = "report", base = file("report"))
    .settings(commonSettings: _*)
    .dependsOn(persistance)
    .dependsOn(commons)
    .dependsOn(messages)
    .dependsOn(models)
    .dependsOn(dataSet)


  lazy val domainService = Project(id = "domainService", base = file("domain-service"))
    .settings(commonSettings: _*)
    .dependsOn(dataSet)
    .dependsOn(dataSource)
    .dependsOn(dbDataSource)
    .dependsOn(dbDataSet)
    .dependsOn(persistance)
    .dependsOn(commons)
    .dependsOn(messages)
    .dependsOn(models)
    .dependsOn(cdataAgent)
    .dependsOn(report)


  lazy val root = Project(id = "mort", base = file("."))
    .aggregate(
      commons,
      models,
      messages,
      persistance,
      dataSet,
      dbDataSet,
      dataSource,
      dbDataSource,
      cdataAgent,
      domainService
    )

}