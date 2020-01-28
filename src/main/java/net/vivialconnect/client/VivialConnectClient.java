package net.vivialconnect.client;

import java.net.Proxy;

/**
 * Requests to the Vivial Connect API may be authenticated using a hash-based message authentication code (HMAC) signature
 * or the HTTP Basic authentication scheme.
 * <p>
 * Basic authentication is simple to use and works well for many applications. However, HMAC provides an added level of security
 * to prevent malicous third parties from tampering with your requests.
 * <p>
 * This class handles a Vivial Connect's account credentials for process requests to the API.
 */
public final class VivialConnectClient {

    /**
     * User's account ID
     */
    private static int accountId;

    /**
     * API key
     */
    private static String apiKey;

    /**
     * API secret
     */
    private static String apiSecret;

    /**
     * API base URL
     */
    private static String apiBaseUrl = "https://api.vivialconnect.net/api/v1.0";

    /**
     * Proxy
     */
    private static Proxy proxy = null;

    private VivialConnectClient() {

    }

    /**
     * Account API key
     *
     * @return API key value
     */
    public static String getApiKey() {
        return apiKey;
    }

    /**
     * Account API Secret
     *
     * @return API secret value
     */
    public static String getApiSecret() {
        return apiSecret;
    }

    /**
     * Account ID of the user
     *
     * @return account ID value
     */
    public static int getAccountId() {
        return accountId;
    }

    /**
     * API base URL
     *
     * @return API base URL
     */
    public static String getApiBaseUrl() {
        return apiBaseUrl;
    }

    /**
     * Overrides the default base URL of the VivialConnect API.
     * <p>
     * (FOR TESTING PURPOSES ONLY) If you'd like your API requests to hit your own
     * (mocked) server, you can set this up here by overriding the base API URL.
     *
     * @param apiBaseUrl the URL the client will use for requests
     */
    public static void overrideApiBaseUrl(String apiBaseUrl) {
        VivialConnectClient.apiBaseUrl = apiBaseUrl;
    }

    /**
     * Initializes the client by setting the accountId, the apiKey, and the apiSecret.
     *
     * @param accountId the VivialConnect account ID
     * @param apiKey    the VivialConnect API key
     * @param apiSecret the VivialConnect API secret
     * @throws IllegalArgumentException if any of the arguments are invalid (ie, null or empty)
     */
    public static void init(int accountId, String apiKey, String apiSecret) {
        validateInitialArguments(accountId, apiKey, apiSecret);

        VivialConnectClient.accountId = accountId;
        VivialConnectClient.apiKey = apiKey;
        VivialConnectClient.apiSecret = apiSecret;
    }

    private static void validateInitialArguments(int accountId, String apiKey, String apiSecret) {
        validateAccountId(accountId);
        validateAPIKey(apiKey);
        validateAPISecret(apiSecret);
    }

    private static void validateAccountId(int accountId) {
        if (accountId < 1) {
            throw createIllegalArgumentException("accountId");
        }
    }

    private static void validateAPIKey(String apiKey) {
        validateStringArg(apiKey, "apiKey");
    }

    private static void validateAPISecret(String apiSecret) {
        validateStringArg(apiSecret, "apiSecret");
    }

    private static void validateStringArg(String arg, String argName) {
        if (arg == null || arg.isEmpty()) {
            throw createIllegalArgumentException(argName);
        }
    }

    private static IllegalArgumentException createIllegalArgumentException(String argName) {
        return new IllegalArgumentException(String.format("'%s' param is not valid", argName));
    }

    /**
     * Set a proxy for connection
     *
     * @param proxy proxy object
     */
    public static void setProxy(Proxy proxy) {
        VivialConnectClient.proxy = proxy;
    }

    /**
     * Proxy object used for connection
     *
     * @return proxy object
     */
    public static Proxy getProxy() {
        return proxy;
    }
}
