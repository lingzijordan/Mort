name := "db-domain-service"

resolvers += "spray repo" at "http://repo.spray.io"

val sprayVersion = "1.3.2"
val slf4jVersion = "1.6.4"
val json4sVersion = "3.2.11"


val spray = Seq(
  "io.spray"    %% "spray-routing"        % sprayVersion,
  "io.spray"    %% "spray-can"            % sprayVersion,
  "io.spray"    %% "spray-servlet"        % sprayVersion,
  "io.spray"    %% "spray-json"           % sprayVersion,
  "io.spray"    %% "spray-testkit"        % sprayVersion,
  "org.slf4j"   %  "slf4j-nop"            % slf4jVersion,
  "org.json4s"  %% "json4s-native"        % json4sVersion,
  "org.json4s"  %% "json4s-ext"           % json4sVersion

)

libraryDependencies ++= spray

Revolver.settings


assemblyMergeStrategy in assembly := {
  case "application.conf" => MergeStrategy.concat
  case "reference.conf" => MergeStrategy.concat
  case "META-INF/MANIFEST.MF" => MergeStrategy.discard
  case "META-INF/ECLIPSEF.RSA" => MergeStrategy.discard
  case "META-INF/ECLIPSEF.SF" => MergeStrategy.discard
  case "META-INF/*.DSA" => MergeStrategy.discard
  case _ => MergeStrategy.first
}

mainClass in assembly := Some("com.bigeyedata.customerdata.domainservice.db.Boot")

assemblyJarName in assembly := "customerData.jar"

test in assembly := {}