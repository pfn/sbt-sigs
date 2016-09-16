package com.hanhuy.sbt.sigs

import sbt._
import sbt.Keys._
import language.existentials

object SigsPlugin extends AutoPlugin {
  override def requires = plugins.JvmPlugin
  override def trigger = allRequirements

  val clazz = InputKey[Unit]("class", "class info")
  val objekt = InputKey[Unit]("object", "object info")

  import autoImport._
  object autoImport {
    val Sigs = config("sigs").hide
  }

  def isScala(cls: Class[_]): Boolean =
      cls.getAnnotations.exists(_.annotationType.getName == "scala.reflect.ScalaSignature")
  override def projectSettings = List(
    clazz <<= Def.inputTask {
      val in = Def.spaceDelimited("<class name>").parsed.mkString(" ")
      val (cl, cls) = classFor((fullClasspath in Sigs).value, in)
      val log = streams.value.log
      if (!isScala(cls)) {
        import language.existentials
        val fields = cls.getDeclaredFields
        val methods = cls.getDeclaredMethods
        val ctors = cls.getConstructors
        val supr = Option(cls.getSuperclass).map("extends " + _.getName).getOrElse("")
        val isint = cls.isInterface
        val isanno = cls.isAnnotation
        val intfs = cls.getInterfaces
        val mods = java.lang.reflect.Modifier.toString(cls.getModifiers)
        val intfss = intfs.map(_.getName).mkString(", ")
        val impls = if (intfs.nonEmpty) s"\nimplements $intfss {" else " {"
        val fs = if (fields.isEmpty) "" else "    " + fields.map(javaMemberToString).mkString("\n    ") + "\n"
        val ctorss = if (ctors.isEmpty) "" else "    " + ctors.map(_.toString + ";").mkString("\n    ") + "\n"
        log.info(
          s"""$mods ${cls.toString} $supr$impls
             |$fs$ctorss    ${methods.map(javaMethodToString).mkString("\n    ")}
             |}""".stripMargin)
      } else {
        import reflect.runtime.universe._
        val m = runtimeMirror(cl)
        val c = m.staticClass(in)
        log.info(s"$c ${c.typeSignature.members.toString.drop(5)}")
      }
    },
    objekt := {
      val in = Def.spaceDelimited("<class name>").parsed.mkString(" ")
      import reflect.runtime.universe._
      val cl = classpath.ClasspathUtilities.toLoader((fullClasspath in Sigs).value.map(_.data))
      val m = runtimeMirror(cl)
      val c = m.staticModule(in)
      val log = streams.value.log
      log.info(s"$c ${c.typeSignature.members.toString.drop(5)}")
    },
    fullClasspath in Sigs <<= fullClasspath in Runtime
  )

  def classFor(cp: Def.Classpath, cls: String): (ClassLoader,Class[_]) = {
    val cl = classpath.ClasspathUtilities.toLoader(cp.map(_.data))
    try {
      val clz = cl.loadClass(cls)
      (cl,clz)
    } catch {
      case e: Exception => throw new MessageOnlyException("Failed to find class: " + e.getMessage)
    }
  }
  def javaMemberToString(f: java.lang.reflect.Field): String = {
    import java.lang.reflect._
    val mods = Modifier.toString(f.getModifiers)
    val mod2 = if (mods.nonEmpty) mods + " " else mods
    val nme = f.getName
    val tpe = f.getGenericType.getTypeName
    s"$mod2$tpe $nme;"
  }
  def javaMethodToString(m: java.lang.reflect.Method): String = {
    import java.lang.reflect._
    val mods = Modifier.toString(m.getModifiers)
    val mods2 = if (mods.nonEmpty) mods + " " else mods
    val ret = m.getGenericReturnType.getTypeName
    val nme = m.getName
    val exs = m.getGenericExceptionTypes
    val es = if (exs.isEmpty) "" else " throws " + exs.map(_.getTypeName).mkString(", ")
    val params = m.getGenericParameterTypes.map(_.getTypeName).mkString(",")
    val ps = if (m.isVarArgs) params.dropRight(2) + "..." else params
    s"$mods2$ret $nme($ps)$es;"
  }
}
