import Dependencies._

name := "models"

initialCommands := "import com.bigeyedata.mor.models._"


libraryDependencies ++= scalikejdbc

libraryDependencies += mysql

scalikejdbcSettings