name := "sag"

version in ThisBuild := "1.0"

scalaVersion in ThisBuild := "2.9.2"

resolvers += "SpringSource Repository" at "http://repo.springsource.org/release"

resolvers += "Spring Maven Release Repository" at "http://repo.springsource.org/libs-release"

libraryDependencies ++= Seq(
	"org.mongodb" % "mongo-java-driver" % "2.9.1",
	"org.springframework.data" % "spring-data-mongodb" % "1.0.3.RELEASE",
	"joda-time" % "joda-time" % "2.1",
	"org.joda" % "joda-convert" % "1.2",
	"commons-logging" % "commons-logging" % "1.1.1",
	"net.sourceforge.htmlunit" % "htmlunit" % "2.11",
	"org.scalatest" %% "scalatest" % "1.6.1" % "test"
)

libraryDependencies ++= Seq("spring-core", "spring-context", "spring-aop", "spring-expression") map ("org.springframework" % _ % "3.2.0.RELEASE")