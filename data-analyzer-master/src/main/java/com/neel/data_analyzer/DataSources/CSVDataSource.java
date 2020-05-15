package com.neel.data_analyzer.DataSources;

public class CSVDataSource extends DataSource {
    DataSourceType type = DataSourceType.CSV;
    String path;

    public CSVDataSource(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}