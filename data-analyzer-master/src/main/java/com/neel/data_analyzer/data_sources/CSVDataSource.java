package com.neel.data_analyzer.data_sources;

import com.neel.data_analyzer.Schema;
import com.neel.data_analyzer.data_sources_meta.DataSourceMeta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CSVDataSource implements DataSource {
    DataSourceMeta dataSourceMeta;
    Schema schema;
}
