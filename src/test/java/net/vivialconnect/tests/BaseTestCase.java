package net.vivialconnect.tests;

import java.util.HashMap;
import java.util.Map;

import net.vivialconnect.tests.data.DataSource;
import net.vivialconnect.tests.data.DataSourceFactory;

public class BaseTestCase {

    private static final DataSource DATA_SOURCE = new DataSourceFactory().createDataSource();

    protected final DataSource getDataSource() {
        return DATA_SOURCE;
    }

    protected Map<String, String> withLimitOf(int limit) {
        Map<String, String> filters = new HashMap<String, String>();
        filters.put("limit", String.valueOf(limit));

        return filters;
    }
}