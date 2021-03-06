name := "sag"

version in ThisBuild := "1.0"

scalaVersion in ThisBuild := "2.10.0"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

seq(com.github.retronym.SbtOneJar.oneJarSettings: _*)

libraryDependencies ++= Seq(
	"com.googlecode.mobilityrpc" % "mobility-rpc" % "1.0.0",
	"com.typesafe.akka" %% "akka-actor" % "2.2-M3",
	"com.typesafe.akka" %% "akka-remote" % "2.2-M3",
	"com.typesafe.akka" %% "akka-kernel" % "2.2-M3"
)
