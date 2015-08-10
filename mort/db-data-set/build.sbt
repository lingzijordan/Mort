import Dependencies._
name := "db-data-set"

libraryDependencies ++= spark

initialCommands := "import com.bigeyedata.mort.dbdataset._"