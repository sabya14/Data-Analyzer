name := "data-lake-writer"

version := "0.1"

scalaVersion := "2.12.11"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.4.4",
  "org.scalactic" %% "scalactic" % "3.1.1",
  "org.scalatest" %% "scalatest" % "3.1.1" % "test",
  "org.scala-lang" % "scala-parser-combinators" % "2.11.0-M4",
  "com.google.code.gson" % "gson" % "2.8.6"
)