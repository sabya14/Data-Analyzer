package data_analyzer.models.DataRow

import java.util

import com.google.gson.Gson


case class JSONArrayDataRow(private val row: util.List[String]) extends DataRow {
  override def getValues: util.List[String] = row
}

//TODO: Not needed
object JSONArrayDataRow {
  def fromSerializedJsonArray(jsonArrayRow: String): JSONArrayDataRow = {
    new JSONArrayDataRow(new Gson().fromJson(jsonArrayRow, classOf[util.List[String]]))
  }
}