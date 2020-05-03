package data_analyzer.writers

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.streaming.StreamingQuery

import scala.util.{Failure, Try}

class HadoopWriter(uri: String) {

  val conf = new Configuration()
  conf.set("fs.defaultFS", uri)
  val hdfs: FileSystem = FileSystem.get(conf)

  def createDir(filePath: String): Unit = {
    try {
      val path = new Path(filePath)
      hdfs.mkdirs(path)
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }

  def writeToHadoop(streamingDf: DataFrame, dir: String, checkpointDir: String): StreamingQuery = {
    createDir(dir)
    createDir(checkpointDir)
    streamingDf.writeStream
      .outputMode("append")
      .format("csv")
      .option("checkpointLocation", checkpointDir)
      .option("path", dir)
      .start()
  }
}
