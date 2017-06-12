package net.vivialconnect.tests.data;

public class DataSourceFactory {

    public DataSource createDataSource() {
        if (APIKeyIsPresent()) {
            return new VivialConnectServer();
        } else {
            return new MockData();
        }
    }

    private boolean APIKeyIsPresent() {
        return System.getProperty("vivialconnect.test.api-key") != null;
    }
}