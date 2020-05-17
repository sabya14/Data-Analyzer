package com.neel.data_analyzer.data_sources_factories;

import com.neel.data_analyzer.Schema;
import com.neel.data_analyzer.data_sources.DataSource;
import com.neel.data_analyzer.data_sources_meta.DataSourceMeta;

import java.io.FileNotFoundException;

public interface DataSourceFactory {
    public DataSource getDataSource();
    public Schema inferSchema() throws FileNotFoundException;
}