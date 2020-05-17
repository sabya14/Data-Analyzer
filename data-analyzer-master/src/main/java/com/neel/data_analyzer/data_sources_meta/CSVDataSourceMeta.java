package com.neel.data_analyzer.data_sources_meta;

import com.neel.data_analyzer.DataSourceType;
import lombok.Getter;

@Getter
public class CSVDataSourceMeta implements DataSourceMeta {
    String filePath;

    public CSVDataSourceMeta(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public DataSourceType getType() {
        return DataSourceType.CSV;
    }
}
