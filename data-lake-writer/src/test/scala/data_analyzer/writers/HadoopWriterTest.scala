package data_analyzer.writers

import com.github.mrpowers.spark.fast.tests.DatasetComparer
import data_analyzer.{HDFSTestWrapper, SparkTestSession}
import org.apache.spark.sql.types.StructType
import org.scalatest.BeforeAndAfterAll

class HadoopWriterTest extends org.scalatest.FunSuite with HDFSTestWrapper with BeforeAndAfterAll with SparkTestSession with DatasetComparer {
  override protected def beforeAll(): Unit = {
    startHDFS()
  }

  override protected def afterAll(): Unit = {
    shutdownHDFS()
  }

  test("should write a dataframe to Hadoop system") {
    val userSchema = new StructType().add("name", "string").add("age", "integer")
    val csvDF = spark
      .read
      .option("sep", ",")
      .schema(userSchema) // Specify schema of the csv files
      .csv(getClass.getResource("/input_dir").getPath) // Equivalent to format("csv").load("/path/to/directory")

    val csvStreamDF = spark
      .readStream
      .option("sep", ",")
      .schema(userSchema) // Specify schema of the csv files
      .csv(getClass.getResource("/input_dir").getPath) // Equivalent to format("csv").load("/path/to/directory")

    val writer = new HadoopWriter(getNameNodeURI)
    val hadoopDir = "user"
    val checkpointDir = "checkPoint"

    val query = writer.writeToHadoop(csvStreamDF, hadoopDir, checkpointDir)
    query.awaitTermination(10000)

    val frame = spark.read.
      option("sep", ",")
      .schema(userSchema)
      .csv(getNameNodeURI+ "/user")

    assertSmallDatasetEquality(csvDF, frame)

  }
}
