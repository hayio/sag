name := "sag"

version in ThisBuild := "1.0"

scalaVersion in ThisBuild := "2.10.0"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
	"com.googlecode.mobilityrpc" % "mobility-rpc" % "1.0.0",
	"com.typesafe.akka" %% "akka-actor" % "2.1.2",
	"com.typesafe.akka" %% "akka-agent" % "2.1.2",
	"com.typesafe.akka" %% "akka-camel" % "2.1.2",
	"com.typesafe.akka" %% "akka-dataflow" % "2.1.2",
	"com.typesafe.akka" %% "akka-file-mailbox" % "2.1.2",
	"com.typesafe.akka" %% "akka-osgi" % "2.1.2",
	"com.typesafe.akka" %% "akka-osgi-aries" % "2.1.2",
	"com.typesafe.akka" %% "akka-remote" % "2.1.2",
	"com.typesafe.akka" %% "akka-slf4j" % "2.1.2",
	"com.typesafe.akka" %% "akka-testkit" % "2.1.2",
	"com.typesafe.akka" %% "akka-transactor" % "2.1.2",
	"com.typesafe.akka" %% "akka-zeromq" % "2.1.2"
)
