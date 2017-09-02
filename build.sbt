name := "freestyle-meta-unit"

scalaVersion := "2.12.2"

version := "0.0.1"


val freestyleVersion = "0.3.1"
val scalametaVersion = "1.8.0"
val scalatestVersion = "3.0.1"

libraryDependencies ++= Seq(
  "io.frees"      %% "freestyle" % freestyleVersion,
  "org.scalameta" %% "scalameta" % scalametaVersion,
  "org.scalatest" %% "scalatest" % scalatestVersion % Test
)

addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M9" cross CrossVersion.full)

scalacOptions += "-Xplugin-require:macroparadise"
scalacOptions in (Compile, console) := Seq()
