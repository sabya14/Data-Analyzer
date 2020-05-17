package com.neel.data_analyzer.data_sources;

import com.neel.data_analyzer.Schema;
import com.neel.data_analyzer.data_sources_meta.CSVDataSourceMeta;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CSVDataSourceTest {

    @Test
    void shouldReturnCorrectSchemaAndMeta() {
        HashMap<Integer, List<String>> schemaDef = new HashMap<>();
        schemaDef.put(1, (Arrays.asList("name", "string")));
        Schema schema = new Schema(schemaDef);
        CSVDataSourceMeta meta = new CSVDataSourceMeta("some_path");
        CSVDataSource csvDataSource = new CSVDataSource(meta, schema);
        assertEquals(csvDataSource.getSchema(), schema);
        assertEquals(csvDataSource.getDataSourceMeta(), csvDataSource);
    }
}