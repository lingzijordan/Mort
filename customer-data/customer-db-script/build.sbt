

name := "customer-db-script"


libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.35"


seq(flywaySettings: _*)

flywayUrl := System.getProperty("flyway.url", "jdbc:mysql://localhost/customerdata_dev")

flywayUser := "bigeye"

flywayPassword := "bigeye123"

flywayBaselineVersion := "0"

flywayBaselineOnMigrate := true