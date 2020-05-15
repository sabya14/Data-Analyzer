package com.neel.data_analyzer.WriterConfigs;

import com.neel.data_analyzer.DataTypes;

import java.util.Map;

public interface WriterConfig {
    public Map<String, DataTypes> inferSchema();
}
