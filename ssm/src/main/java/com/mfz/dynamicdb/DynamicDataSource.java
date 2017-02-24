package com.mfz.dynamicdb;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by fanzhenmeng on 2017/2/22.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDsKey();
    }
}

