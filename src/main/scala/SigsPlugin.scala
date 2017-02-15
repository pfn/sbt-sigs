package com.hanhuy.sbt.sigs

import sbt._
import sbt.Keys._
import Cache.StringFormat
import Cache.seqFormat

object SigsPlugin extends AutoPlugin {
  override def requires = plugins.JvmPlugin
  override def trigger = allRequirements

  val scalap = InputKey[Unit]("scalap", "run scalap")
  val javap = InputKey[Unit]("javap", "run javap")

  val Sigs = config("sigs").hide

  val discoverAllClasses = TaskKey[Seq[String]](
    "sigs-discover-all-classes", "all known-in-project class names") in Sigs

  def toolsJar: File = {
    val home = file(sys.props("java.home"))
    val jdk = if (home.getName.toLowerCase == "jre") {
      home.getParentFile
    } else home
    val toolsjar = jdk / "lib" / "tools.jar"
    if (!toolsjar.isFile) {
      throw new MessageOnlyException("Unable to locate JDK tools.jar")
    }

    toolsjar
  }
  def allClasses:
    Def.Initialize[State => sbt.complete.Parser[(Seq[String],Option[String])]] =
      loadForParser(discoverAllClasses) { (s, stored) =>
        import sbt.complete.Parser
        import sbt.complete.DefaultParsers._
        val res = stored.getOrElse(Seq.empty)
        val options: Parser[String] = token(
          ("-" ~ NotSpace).map(s => s._1 + s._2) examples "-help")
        EOF.map(_ => (Nil,None)) |
          ((EOF.map(_ => Nil) | (Space ~> Parser.zeroOrMore(options))) ~
            (Space ~> Parser.opt(token(NotSpace examples res.toSet))))
      }

  def discoverClasses(a: inc.Analysis) =
    Tests.allDefs(a).map(_.name).toSet.toSeq.sorted

  override def projectConfigurations = Sigs :: Nil

  override def projectSettings = List(
    libraryDependencies <+= Def.setting {
      "org.scala-lang" % "scalap" % scalaVersion.value % Sigs.name
    },
    discoverAllClasses <<= (compile in Compile) map
      discoverClasses storeAs
        discoverAllClasses triggeredBy (compile in Compile),
    javap <<= Def.inputTask {
      val (flags, clz) = allClasses.parsed
      val forkopts = ForkOptions(
        runJVMOptions = "-classpath" :: toolsJar.getAbsolutePath :: Nil
      )

      val fork = new ForkRun(forkopts)
      val fcp = (fullClasspath in Compile).value.map(
        _.data.getAbsolutePath).mkString(java.io.File.pathSeparator)
      val cp = "-classpath" :: fcp :: Nil
      this.synchronized {
        Fork.java(forkopts,
          List("com.sun.tools.javap.Main") ++ cp ++ flags ++ clz)
      }
    },
    scalap <<= Def.inputTask {
      val (flags, clz) = allClasses.parsed
      val scalapPath = (managedClasspath in Sigs).value.map(
        _.data.getAbsolutePath).mkString(java.io.File.pathSeparator)
      val forkopts = ForkOptions(
        runJVMOptions = "-classpath" :: scalapPath :: Nil
      )
      val fcp = (fullClasspath in Compile).value.map(
        _.data.getAbsolutePath).mkString(java.io.File.pathSeparator)
      val cp = "-classpath" :: fcp :: Nil
      this.synchronized {
        Fork.java(forkopts,
          List("scala.tools.scalap.Main") ++ cp ++ flags ++ clz)
      }
    },
    managedClasspath in Sigs :=
      Classpaths.managedJars(Sigs, classpathTypes.value, update.value)
  )
}
