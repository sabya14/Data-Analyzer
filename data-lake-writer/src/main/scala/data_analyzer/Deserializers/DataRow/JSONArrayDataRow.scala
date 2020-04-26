package data_analyzer.Deserializers.DataRow

import java.util

import com.google.gson.Gson


class JSONArrayDataRow(jsonArrayRow: String) extends DataRow {
  override def getValues: util.List[String] = {
    new Gson().fromJson(jsonArrayRow, classOf[util.List[String]])
  }
}
