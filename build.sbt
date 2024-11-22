ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.4"

lazy val root = (project in file("."))
  .settings(
    name := "DataPipeline"
  )

libraryDependencies ++= Seq(
  "org.apache.poi" % "poi" % "5.3.0",
  "org.apache.poi" % "poi-ooxml" % "5.3.0",
  "org.apache.poi" % "poi-ooxml-lite" % "5.3.0"
)