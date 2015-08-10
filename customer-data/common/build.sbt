name := "common"

initialCommands := "import com.bigeyedata.customerdata.common._"

val mySqlDriverVersion = "5.1.35"

val db = Seq(
  "mysql"     % "mysql-connector-java"      % mySqlDriverVersion
)

libraryDependencies ++= db