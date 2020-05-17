package com.neel.data_analyzer.data_sources;

import com.neel.data_analyzer.Schema;
import com.neel.data_analyzer.data_sources_factories.CSVDataSourceFactory;
import com.neel.data_analyzer.data_sources_factories.DataSourceFactory;
import com.neel.data_analyzer.data_sources_meta.CSVDataSourceMeta;
import com.neel.data_analyzer.data_sources_meta.DataSourceMeta;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DataSourceFactoryTest {

    @Test
    void shouldReturnCSVDataSourceWithCorrectSchemaIfCorrectMetaGiven() {
        String path = DataSourceFactoryTest.class.getClassLoader().getResource("test.csv").getPath();
        DataSourceMeta dataSourceMeta = new CSVDataSourceMeta(path);
        HashMap<Integer, List<String>> schemaDef = new HashMap<>();
        schemaDef.put(1, Arrays.asList("Roll", "Integer"));
        schemaDef.put(2, Arrays.asList("Age", "Integer"));
        schemaDef.put(3, Arrays.asList("Name", "String"));
        Schema expectedSchema = new Schema(schemaDef);

        CSVDataSourceFactory dataSourceFactory = new CSVDataSourceFactory(dataSourceMeta);
        CSVDataSource dataSource = (CSVDataSource) dataSourceFactory.getDataSource();
        assertEquals(dataSource.getSchema(), expectedSchema);
    }
}
