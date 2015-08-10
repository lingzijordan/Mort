logLevel := Level.Warn

addSbtPlugin("com.github.mpeltonen" %% "sbt-idea" % "1.6.0")


addSbtPlugin("io.spray" % "sbt-revolver" % "0.7.2")


libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.35"

addSbtPlugin("org.scalikejdbc" %% "scalikejdbc-mapper-generator" % "2.2.7")

addSbtPlugin("io.spray" % "sbt-revolver" % "0.7.2")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.13.0")