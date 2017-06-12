package net.vivialconnect.tests;

import org.junit.Test;

import net.vivialconnect.client.VivialConnectClient;

public class ClientInitTest {

    private static final int INVALID_ACCOUNT_ID = 0;
    private static final String INVALID_API_KEY = "";
    private static final String INVALID_API_SECRET = "";

    protected static final int ACCOUNT_ID = 6242736;
    protected static final String API_KEY = "__my_test_key__";
    protected static final String API_SECRET = "__my_test_secret__";

    @Test(expected = IllegalArgumentException.class)
    public void client_initialization_with_invalid_account_id_throws_illegal_argument_exception() {
        VivialConnectClient.init(INVALID_ACCOUNT_ID, API_KEY, API_SECRET);
    }

    @Test(expected = IllegalArgumentException.class)
    public void client_initialization_with_invalid_api_key_throws_illegal_argument_exception() {
        VivialConnectClient.init(ACCOUNT_ID, INVALID_API_KEY, API_SECRET);
    }

    @Test(expected = IllegalArgumentException.class)
    public void client_initialization_with_invalid_api_secret_throws_illegal_argument_exception() {
        VivialConnectClient.init(ACCOUNT_ID, API_KEY, INVALID_API_SECRET);
    }
}