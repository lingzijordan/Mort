import Dependencies._


name := "domain-service"

resolvers += "spray repo" at "http://repo.spray.io"

libraryDependencies ++= spray
libraryDependencies += json4sExt
libraryDependencies += logback

Revolver.settings
parallelExecution in Test := false


assemblyMergeStrategy in assembly := {
  case "application.conf" => MergeStrategy.concat
  case "reference.conf" => MergeStrategy.concat
  case "META-INF/MANIFEST.MF" => MergeStrategy.discard
  case "META-INF/ECLIPSEF.RSA" => MergeStrategy.discard
  case "META-INF/ECLIPSEF.SF" => MergeStrategy.discard
  case "META-INF/*.DSA" => MergeStrategy.discard
  case _ => MergeStrategy.first
}

mainClass in assembly := Some("com.bigeyedata.mort.Main")

assemblyJarName in assembly := "mort.jar"

test in assembly := {}