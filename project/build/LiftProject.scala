import sbt._

class LiftProject(info: ProjectInfo) extends DefaultWebProject(info) {
  val liftVersion = "2.1"

  val mavenLocal = "Local Maven Repository" at "file://" + Path.userHome+ "/.m2/repository"

  override def libraryDependencies = Set(
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile->default",
    "net.liftweb" %% "lift-mapper" % liftVersion % "compile->default",
    "net.liftweb" %% "lift-openid" % liftVersion % "compile->default",
    "org.mortbay.jetty" % "jetty" % "6.1.22" % "test->default",
    "junit" % "junit" % "4.5" % "test->default",
    "org.scala-tools.testing" %% "specs" % "1.6.5" % "test->default",
    "com.h2database" % "h2" % "1.2.138",
    "org.scala_libs" %% "lift-gae-jdo" % "1.0.1" % "compile->default"

  ) ++ super.libraryDependencies
}
