package com.neel.data_analyzer.data_sources_meta;

import com.neel.data_analyzer.DataSourceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CSVDataSourceMetaTest {

    @Test
    void shouldReturnCorrectType() {
        CSVDataSourceMeta csvSourceMeta = new CSVDataSourceMeta("somepath");
        assertEquals(csvSourceMeta.getType(), DataSourceType.CSV);
    }
}