import Dependencies._
name := "customer-data-agent"

initialCommands := "import com.bigeyedata.mort.cdataagent._"

resolvers += "spray repo" at "http://repo.spray.io"
val sprayVersion = "1.3.2"

val sprayclient = Seq(
  "io.spray" %% "spray-client" % sprayVersion
)

//val json4s = "org.json4s" %% "json4s-native" % "3.2.11"

libraryDependencies ++= sprayclient
//libraryDependencies += json4s

Revolver.settings
parallelExecution := false
