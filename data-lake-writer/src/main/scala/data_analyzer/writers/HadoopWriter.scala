package data_analyzer.writers

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.streaming.StreamingQuery

class HadoopWriter(val uri: String) {

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
    val fullFilePath = s"${uri}/${dir}"
    createDir(fullFilePath)
    val fullCPPath = s"${uri}/${checkpointDir}"
    createDir(fullCPPath)
    streamingDf.writeStream
      .outputMode("append")
      .format("csv")
      .option("checkpointLocation", fullCPPath)
      .option("path", fullFilePath)
      .start()
  }
}
