import bintray.Keys._

name := "sbt-sigs"
organization := "com.hanhuy.sbt"

version := "0.1-SNAPSHOT"

scalacOptions ++= Seq("-deprecation","-Xlint","-feature")

libraryDependencies ++=
  "com.hanhuy.sbt" %% "bintray-update-checker" % "0.2" ::
  Nil

sbtPlugin := true

// build info plugin
buildInfoSettings
sourceGenerators in Compile <+= buildInfo
buildInfoPackage := "com.hanhuy.sbt.sigs"

// bintray
bintrayPublishSettings
repository in bintray := "sbt-plugins"
publishMavenStyle := false
licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
bintrayOrganization in bintray := None

// scripted
scriptedSettings
scriptedLaunchOpts ++= "-Xmx1024m" ::
  "-Dplugin.version=" + version.value ::
  Nil
