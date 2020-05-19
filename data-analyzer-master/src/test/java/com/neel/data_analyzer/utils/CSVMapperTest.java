package com.neel.data_analyzer.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CSVMapperTest {

    @Test
    void shouldReturnCorrectlyParseCSVRows() {
        List<String> rowList = asList("\"“neel,s”\",12", "\"“neel,s”\",13");
        CSVMapper csvMapper = new CSVMapper(rowList);
        List<List<String>> expectedParsedRows = asList(asList("“neel,s”", "12"), asList("“neel,s”", "13"));
        List<List<String>> parsedRows = csvMapper.parseRows();
        assertEquals(expectedParsedRows, parsedRows);

    }
}