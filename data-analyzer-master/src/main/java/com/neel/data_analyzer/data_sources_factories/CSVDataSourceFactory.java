package com.neel.data_analyzer.data_sources_factories;

import com.neel.data_analyzer.Schema;
import com.neel.data_analyzer.data_sources.CSVDataSource;
import com.neel.data_analyzer.data_sources.DataSource;
import com.neel.data_analyzer.data_sources_meta.CSVDataSourceMeta;
import com.neel.data_analyzer.data_sources_meta.DataSourceMeta;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVDataSourceFactory implements DataSourceFactory{

    CSVDataSourceMeta dataSourceMeta;
    DataSource dataSource;

    public CSVDataSourceFactory(DataSourceMeta dataSourceMeta) {
        this.dataSourceMeta = (CSVDataSourceMeta) dataSourceMeta;
    }

    public DataSource getDataSource() {
        return null;
    }

    @Override
    public Schema inferSchema() throws FileNotFoundException {
        return null;
    }
}