

name := "metadata-db-script"


libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.35"


seq(flywaySettings: _*)

flywayUrl := System.getProperty("flyway.url", "jdbc:mysql://localhost/bigeye_dev")

flywayUser := "bigeye"

flywayPassword := "bigeye123"

flywayBaselineVersion := "0"

flywayBaselineOnMigrate := true