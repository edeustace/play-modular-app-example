import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName = "corespring"
  val appVersion = "1.0-SNAPSHOT"
  val compilerVersion = "2.10.0"

  //Just a regular sbt project
  val qti = Project(appName + "-qti", file("modules/qti"))
    .settings( scalaVersion := compilerVersion)
  //A Play project
  val common = play.Project(appName + "-common", appVersion, path = file("modules/common"))
    .settings( scalaVersion := compilerVersion)

  //The web layer
  val api = play.Project(appName + "-api", appVersion, path = file("modules/api")).dependsOn(common)
    .settings( scalaVersion := compilerVersion)
  val webPublic = play.Project(appName + "-web-public", appVersion, path = file("modules/web-public")).dependsOn(common)
    .settings( scalaVersion := compilerVersion)
  val webAdmin = play.Project(appName + "-web-admin", appVersion, path = file("modules/web-admin")).dependsOn(common)
    .settings( scalaVersion := compilerVersion)

  val appDependencies = Seq(
    // Add your project dependencies here,
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
    scalaVersion := compilerVersion
  )
    .dependsOn(webAdmin, webPublic, api)
    .aggregate(webAdmin, webPublic, api)

}
