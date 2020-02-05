package net.vivialconnect.model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Date;
import java.util.Arrays;
import java.util.SimpleTimeZone;
import java.util.Locale;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.vivialconnect.client.VivialConnectClient;
import net.vivialconnect.http.CanonicalRequestBuilder;
import net.vivialconnect.model.account.Account;
import net.vivialconnect.model.error.ErrorMessage;
import net.vivialconnect.model.error.NoContentException;
import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.format.JsonBodyBuilder;
import net.vivialconnect.model.message.BulkInfo;
import net.vivialconnect.model.message.BulkInfoCollection;
import net.vivialconnect.model.message.MessageCollection;
import net.vivialconnect.util.CryptoUtils;
import net.vivialconnect.util.ReflectionUtils;
import net.vivialconnect.util.ProjectProperties;

/**
 * Base class that provides properties and methods for API resource creation, request and response handling.
 */
public abstract class VivialConnectResource implements Serializable {

    private static final long serialVersionUID = -2726239361148612818L;

    private static final String SIGNATURE_ALGORITHM = "HmacSHA256";

    protected static final String ISO_8601_FORMAT = "yyyyMMdd'T'HHmmss'Z'";
    protected static final String HTTP_DATE_FORMAT = "E, dd MMM yyyy HH:mm:ss z";

    protected static Set<Class<?>> classesWithoutRootValue = new HashSet<Class<?>>();

    static {
        classesWithoutRootValue.add(ResourceCount.class);
        classesWithoutRootValue.add(BulkInfo.class);
        classesWithoutRootValue.add(BulkInfoCollection.class);
        classesWithoutRootValue.add(MessageCollection.class);

    }

    protected enum RequestMethod {
        GET, POST, PUT, DELETE
    }

    /**
     * Create a URL by parsing the given class type and suffix. The new URL created will be:
     * <code>
     * {scheme}://{api-base-url}/{account-id}/{class}/{suffix}.json
     * </code>
     * Example:
     * <code>
     * /api/v1.0/accounts/123/messages/count.json
     * </code>
     *
     * @param clazz  class type
     * @param suffix string value with a suffix for the URL
     * @return a new URL with with the class and suffix values
     */
    protected static String classURLWithSuffix(Class<?> clazz, String suffix) {
        return String.format("%ss/%s.json", singleClassURL(clazz), suffix);
    }

    /**
     * Create a URL by parsing the given class type, a value and a resource name.
     * Example:
     * <code>
     * /accounts/123/numbers/(int: id)/tags.json
     * </code>
     *
     * @param clazz        class type
     * @param value        a value: numeric or string
     * @param resourceName a resource name
     * @return a new URL with the class, value and resource values
     */
    protected static String classURLWithResourceSuffix(Class<?> clazz, String value, String resourceName) {
        return String.format("%ss/%s/%s.json", singleClassURL(clazz), value, resourceName);
    }


    /**
     * Create an URL by parsing a given class type.
     * Example:
     * <code>
     * /accounts/123/messages.json
     * </code>
     *
     * @param clazz class type
     * @return an URL with the class type value
     */
    protected static String classURL(Class<?> clazz) {
        return String.format("%ss.json", singleClassURL(clazz));
    }


    /**
     * Create an URL by parsing a given class type and creating an intermediary result.
     * <p>
     * This method is used for create the main structure of an API URL.
     * <p>
     * Example:
     * <code>
     * /accounts/123/message
     * </code>
     * <p>
     * The rest must be complete for the caller of this method.
     *
     * @param clazz class type
     * @return an intermediary URL
     */
    protected static String singleClassURL(Class<?> clazz) {
        if (Account.class.equals(clazz)) {
            return String.format("%s/accounts/%d.json", VivialConnectClient.getApiBaseUrl(), VivialConnectClient.getAccountId());
        }

        return String.format("%s/accounts/%d/%s", VivialConnectClient.getApiBaseUrl(),
                VivialConnectClient.getAccountId(),
                ReflectionUtils.className(clazz).toLowerCase());
    }

    /**
     * URL without plural with `.json` suffix
     *
     * @param resourceName resource name value
     * @return a new URL with a single name and .json value
     */
    protected static String unmappedURL(String resourceName) {
        return String.format("%s.json", formatURLForResource(resourceName));
    }

    /**
     * Create an URL in singular
     *
     * @param resourceName resource name value
     * @return a new URL value
     */
    protected static String formatURLForResource(String resourceName) {
        return String.format("%s/accounts/%d/%s", VivialConnectClient.getApiBaseUrl(),
                VivialConnectClient.getAccountId(),
                resourceName);
    }

    /**
     * Taking a HTTP method, an URL, a body and query params, do a request to the Vivial Connect API and process the
     * response returned.
     * <p>
     * If the request does not have a body and/or query params, pass null to these parameters.
     *
     * @param method        a valid HTTP method
     * @param url           request URL
     * @param body          body or payload of the request. This is not required.
     * @param queryParams   query params for the request. This is not required.
     * @param responseClass class type for map the response
     * @param <T>           Response class type
     * @return a response type defined
     * @throws VivialConnectException if an error occurs at API-level
     */
    protected static <T> T request(VivialConnectResource.RequestMethod method,
                                   String url, String body, Map<String, String> queryParams,
                                   Class<T> responseClass) throws VivialConnectException {
        try {
            URL endpoint = createEndpoint(url, method, queryParams);
            Date currentDate = new Date();

            String requestTimestamp = createRequestTimestamp(currentDate);
            String requestDate = createRequestDate(currentDate);

            Map<String, String> headers = new HashMap<String, String>();
            headers.put("Date", requestDate);
            headers.put("Host", endpoint.getHost());
            headers.put("Accept", "application/json");

            if (requestSupportsBody(method.name())) {
                headers.put("Content-Type", "application/json");
            }

            CanonicalRequestBuilder canonicalRequestbuilder = new CanonicalRequestBuilder();
            canonicalRequestbuilder.endpoint(endpoint)
                    .requestTimestamp(requestTimestamp)
                    .body(body)
                    .method(method.name())
                    .headers(headers)
                    .queryParams(queryParams);

            String canonicalRequest = canonicalRequestbuilder.build();
            String signedHeaders = canonicalRequestbuilder.getCanonicalizedHeaderNames();
            String signature = createSignature(canonicalRequest);

            String authoritzationHeader = buildAuthoritzationHeader(signature);
            headers.put("Authorization", authoritzationHeader);
            headers.put("X-Auth-Date", requestTimestamp);
            headers.put("X-Auth-SignedHeaders", signedHeaders);
            String appVersion = ProjectProperties.getProperty("application.version");
            headers.put("User-Agent", "VivialConnect JavaClient " + appVersion);

            Map<String, String> xUserAgent = new HashMap<String, String>();
            xUserAgent.put("client_version", appVersion);
            xUserAgent.put("lang", "Java");
            xUserAgent.put("lang_version", System.getProperty("java.version"));
            xUserAgent.put("publisher", "vivialconnect");
            xUserAgent.put("platform", System.getProperty("os.name") + " " +
                    System.getProperty("os.version") + " " +
                    System.getProperty("os.arch"));
            ObjectMapper mapper = new ObjectMapper();
            headers.put("X-VivialConnect-User-Agent", mapper.writeValueAsString(xUserAgent));

            return request(endpoint, method, headers, queryParams, body, responseClass);
            /* return jerseyRequest(endpoint, method, headers, queryParams, body, responseClass); */
        } catch (NoContentException nce) {
            throw nce;
        } catch (Exception e) {
            VivialConnectException vivialConnectException = handleException(e);
            throw vivialConnectException;
        }
    }


    private static VivialConnectException handleException(Exception e) {
        VivialConnectException vce = null;

        if (VivialConnectException.class.isAssignableFrom(e.getClass())) {
            vce = (VivialConnectException) e;
        } else {
            vce = new VivialConnectException(e);
        }

        return vce;
    }


    private static boolean requestSupportsBody(String method) {
        String[] supportedMethods = {"DELETE", "POST", "PUT"};

        return Arrays.binarySearch(supportedMethods, method) > -1;
    }


    private static URL createEndpoint(String url, RequestMethod method,
                                      Map<String, String> queryParams) throws MalformedURLException {
        if (method == RequestMethod.GET && queryParams != null && !queryParams.isEmpty()) {
            StringBuilder urlBuilder = new StringBuilder(url).append("?");
            for (String key : queryParams.keySet()) {
                try {
                    String value = URLEncoder.encode(queryParams.get(key), "UTF-8");
                    urlBuilder.append(key).append("=").append(value).append("&");
                } catch (UnsupportedEncodingException e) {
                    throw new MalformedURLException(e.getMessage());
                }
            }
            url = urlBuilder.deleteCharAt(urlBuilder.length() - 1).toString();
        }
        return new URL(url);
    }

    /**
     * Create a timestamp in format ISO 8601. This value is used to create the HMAC signature and Date header.
     *
     * @param currentDate current date
     * @return timestamp in format 8601
     */
    protected static String createRequestTimestamp(Date currentDate) {
        DateFormat iso8601 = new SimpleDateFormat(ISO_8601_FORMAT);
        iso8601.setTimeZone(new SimpleTimeZone(0, "GMT"));

        return iso8601.format(currentDate);
    }

    private static String createRequestDate(Date currentDate){
        DateFormat requestDateFormat = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);
        requestDateFormat.setTimeZone(new SimpleTimeZone(0, "GMT"));

        return requestDateFormat.format(currentDate);
    }

    private static String createSignature(String canonicalRequest) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        Mac hmac = Mac.getInstance(SIGNATURE_ALGORITHM);
        SecretKeySpec secretKey = new SecretKeySpec(VivialConnectClient.getApiSecret().getBytes(),
                SIGNATURE_ALGORITHM);
        hmac.init(secretKey);

        byte[] signatureBytes = hmac.doFinal(canonicalRequest.getBytes("UTF-8"));
        return CryptoUtils.toHex(signatureBytes);
    }


    private static String buildAuthoritzationHeader(String signature) {
        return new StringBuilder().append("HMAC ")
                .append(VivialConnectClient.getApiKey())
                .append(":")
                .append(signature)
                .toString();
    }

    private static <T> T request(URL endpoint, VivialConnectResource.RequestMethod method, Map<String, String> headers,
                                 Map<String, String> queryParams, String body, Class<T> responseClass)
            throws IOException, NoContentException, VivialConnectException {

        HttpURLConnection connection = null;

        try {
            connection = prepareConnection(endpoint, method);
            setHeaders(connection, headers);
            setBody(connection, body);

            String response = doRequest(connection);

            return unmarshallResponse(response, responseClass);
        } finally {
            disconnect(connection);
        }
    }

    private static HttpURLConnection prepareConnection(URL endpoint, RequestMethod method) throws IOException {
        Proxy proxy = VivialConnectClient.getProxy();

        HttpURLConnection connection = null;
        if (proxy == null) {
            connection = (HttpURLConnection) endpoint.openConnection();
        } else {
            /* If we call this method, this preempts the systems proxy settings, if any.
             * We do the call this way so that if the user sets the proxy using the
             * System.setProperty("http.proxyHost", "something"), it's respected.
             */
            connection = (HttpURLConnection) endpoint.openConnection(proxy);
        }

        connection.setRequestMethod(method.name());
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        return connection;
    }


    private static void setHeaders(HttpURLConnection connection, Map<String, String> headers) {
        for (String headerName : headers.keySet()) {
            String headerValue = headers.get(headerName);
            connection.setRequestProperty(headerName, headerValue);
        }
    }


    private static void setBody(HttpURLConnection connection, String body) throws IOException {
        if (requestSupportsBody(connection.getRequestMethod()) && body != null && !body.isEmpty()) {
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            BufferedWriter outputStream = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));
            outputStream.write(body);
            outputStream.flush();
            outputStream.close();
            wr.close();
        }
    }


    private static String doRequest(HttpURLConnection connection) throws NoContentException, VivialConnectException {
        BufferedReader reader = null;

        try {
            InputStream inputStream = connection.getInputStream();
            reader = createBufferedReader(inputStream);

            String response = readResponse(reader);
            if (connection.getResponseCode() == 204 /* No Content */) {
                throw new NoContentException();
            }

            return response;
        } catch (IOException ioe) {
            throw convertToVivialException(ioe, connection);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw convertToVivialException(e, null);
                }
            }
        }
    }


    private static VivialConnectException convertToVivialException(IOException ioe, HttpURLConnection connection) {
        if (connection == null) {
            return new VivialConnectException(ioe);
        }

        BufferedReader reader = null;

        try {
            reader = createBufferedReader(connection.getErrorStream());
            String errorResponse = readResponse(reader);
            ErrorMessage errorMessage = unmarshalErrorResponse(errorResponse);

            VivialConnectException vivialException = new VivialConnectException(errorMessage.getErrorCode(),
                    errorMessage.getErrorMessage(),
                    connection.getResponseCode(),
                    ioe);

            return vivialException;
        } catch (IOException e) {
            return new VivialConnectException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    return new VivialConnectException(e);
                }
            }
        }
    }


    private static ErrorMessage unmarshalErrorResponse(String errorResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object unmarshalledObject = mapper.reader()
                    .forType(ErrorMessage.class)
                    .readValue(errorResponse);

            ErrorMessage errorMessage = (ErrorMessage) unmarshalledObject;
            return errorMessage;
        } catch (Exception e) {
            return new ErrorMessage(errorResponse, 0);
        }
    }


    private static BufferedReader createBufferedReader(InputStream inputStream) throws IOException {
        return new BufferedReader(new InputStreamReader(inputStream));
    }


    private static String readResponse(BufferedReader reader) throws IOException {
        StringBuilder responseBuilder = new StringBuilder();
        String line = null;

        while ((line = reader.readLine()) != null) {
            responseBuilder.append(line).append("\n");
        }

        return responseBuilder.toString();
    }


    private static <T> T unmarshallResponse(String response, Class<T> responseClass) throws JsonProcessingException, IOException {
        ObjectMapper mapper = configureObjectMapper(responseClass);
        return mapper.reader().forType(responseClass).readValue(response);
    }


    private static <T> ObjectMapper configureObjectMapper(Class<T> responseClass) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        if (shouldUnwrapRoot(responseClass)) {
            mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
        }

        return mapper;
    }


    private static boolean shouldUnwrapRoot(Class<?> responseClass) {
        return !classesWithoutRootValue.contains(responseClass);
    }


    private static void disconnect(HttpURLConnection connection) {
        if (connection != null) {
            connection.disconnect();
        }
    }

    /**
     * Given a key and value returns a map with the values. If this method returns a Map, it will add those value
     * to the instance of the map instead.
     *
     * @param key         param key
     * @param value       param value
     * @param queryParams an instance of Map, if not it will create a new map and return it
     * @return a Map instance with the key-value added.
     */
    protected static Map<String, String> addQueryParam(String key, String value, Map<String, String> queryParams) {
        if (queryParams == null) {
            queryParams = new HashMap<String, String>();
        }

        queryParams.put(key, value);
        return queryParams;
    }

    /**
     * Check the params are valid before adding them to the builder
     *
     * @param builder   JSONBuilder instance
     * @param paramName param name
     * @param value     value name
     */
    protected static void ifParamValidAddToBuilder(JsonBodyBuilder builder, String paramName, String value) {
        if (value != null && !value.isEmpty()) {
            builder.addParamPair(paramName, value);
        }
    }

    /**
     * Validate that an integer param value is valid before add it to the builder
     *
     * @param builder   JSONBuider isntance
     * @param paramName param name
     * @param intValue  integer value
     */
    protected static void ifParamValidAddToBuilder(JsonBodyBuilder builder, String paramName, int intValue) {
        if (intValue > 0) {
            builder.addParamPair(paramName, intValue);
        }
    }
}
