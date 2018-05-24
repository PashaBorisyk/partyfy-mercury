enablePlugins(ScalaJSPlugin)

name := "hipe-front"

version := "0.1"

scalaVersion := "2.12.6"

scalaJSUseMainModuleInitializer := true

skip in packageJSDependencies := false

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.5"
libraryDependencies += "com.lihaoyi" %%% "upickle" % "0.6.6"