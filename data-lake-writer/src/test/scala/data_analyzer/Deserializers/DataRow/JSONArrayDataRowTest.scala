package data_analyzer.Deserializers.DataRow

import java.util

import com.google.gson.Gson

class JSONArrayDataRowTest extends org.scalatest.FunSuite {

  test("should seperate message based on comma") {
    // "neel,s",12
    val jsonArray = "[\"\\\"neel,s\\\"\", \"12\"]";
    val row = new JSONArrayDataRow(jsonArray).getValues
    val expected = java.util.Arrays.asList("\"neel,s\"", "12")
    assert(row == expected)


  }
}
