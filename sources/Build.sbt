name := "sag"

version in ThisBuild := "1.0"

scalaVersion in ThisBuild := "2.10.0"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
	"com.googlecode.mobilityrpc" % "mobility-rpc" % "1.0.0",
	"com.typesafe.akka" %% "akka-actor" % "2.1.2",
	"com.typesafe.akka" %% "akka-remote" % "2.1.2"
)
