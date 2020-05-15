package com.neel.data_analyzer.WriterConfigs;

import com.neel.data_analyzer.DataSources.CSVDataSource;
import com.neel.data_analyzer.DataTypes;

import java.util.Map;

public class CSVWriterConfig implements WriterConfig {

    CSVDataSource csvDataSource;

    public Map<String, DataTypes> inferSchema() {
        return null;
    }
}
