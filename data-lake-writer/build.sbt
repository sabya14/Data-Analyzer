name := "data-lake-writer"

version := "0.1"

scalaVersion := "2.12.11"
val sparkVersion = "2.4.5"
resolvers += "Spark Packages Repo" at "http://dl.bintray.com/spark-packages/maven"


libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.scalactic" %% "scalactic" % "3.1.1",
  "org.scala-lang" % "scala-parser-combinators" % "2.11.0-M4",
  "com.google.code.gson" % "gson" % "2.8.6",
  "org.apache.spark" % "spark-streaming-kafka-0-10_2.12" % sparkVersion,
  "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,

  "org.scalatest" %% "scalatest" % "3.1.1" % "test",
  "MrPowers" % "spark-fast-tests" % "0.20.0-s_2.12" % Test,
  "io.github.embeddedkafka" %% "embedded-kafka" % "2.4.1.1" % Test

)