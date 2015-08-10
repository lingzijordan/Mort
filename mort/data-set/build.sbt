import Dependencies._
name := "data-set"

libraryDependencies ++= spark
initialCommands := "import com.bigeyedata.dataset._"
