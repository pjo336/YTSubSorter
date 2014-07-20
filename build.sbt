name := """YTSubSorter"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "com.google.api-client" % "google-api-client" % "1.18.0-rc",
  "com.google.http-client" % "google-http-client-jackson2" % "1.18.0-rc",
  "com.google.oauth-client" % "google-oauth-client" % "1.18.0-rc",
  "com.google.oauth-client" % "google-oauth-client-jetty" % "1.18.0-rc",
  "com.google.apis" % "google-api-services-youtube" % "v3-rev40-1.13.2-beta"
)
