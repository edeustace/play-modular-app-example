import sbt._
import Keys._
import play.Project._

object Build extends sbt.Build {

  val appName = "corespring"
  val appVersion = "1.0-SNAPSHOT"
  val compilerVersion = "2.10.0"

  //A Play project

  val sharedSettings: Seq[Project.Setting[_]] = Seq(
    scalaVersion := compilerVersion,
    resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
    resolvers += "Sonatype OSS Public Repository" at "http://oss.sonatype.org/content/groups/public"
  )

  val salatSettings: Seq[Project.Setting[_]] = Seq(
    routesImport += "se.radley.plugin.salat.Binders._",
    templatesImport += "org.bson.types.ObjectId"
  )

  val commonDependencies = Seq(
    "se.radley" %% "play-plugins-salat" % "1.2"
  )

  val common = play.Project(appName + "-common", appVersion, commonDependencies, path = file("modules/common"))
    .settings(sharedSettings: _*)

  //The web layer
  val api = play.Project(appName + "-api", appVersion, path = file("modules/api")).dependsOn(common)
    .settings(sharedSettings ++ salatSettings: _*)

  val webPublic = play.Project(appName + "-web-public", appVersion, path = file("modules/web-public")).dependsOn(common)
    .settings(sharedSettings: _*)

  val webAdmin = play.Project(appName + "-web-admin", appVersion, path = file("modules/web-admin")).dependsOn(common)
    .settings(sharedSettings: _*)

  val appDependencies = Seq(
    // Add your project dependencies here,
  )


  val main = play.Project(appName, appVersion, appDependencies)
    .settings(sharedSettings: _*)
    .dependsOn(webAdmin, webPublic, api)
    .aggregate(webAdmin, webPublic, api)

}
