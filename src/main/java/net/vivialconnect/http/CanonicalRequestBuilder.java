package net.vivialconnect.http;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import net.vivialconnect.util.CryptoUtils;

public class CanonicalRequestBuilder{

    private URL endpoint;

    private String method;
    private String body;
    private String requestTimestamp;

    private Map<String, String>	headers;
    private Map<String, String>	queryParams;


    public CanonicalRequestBuilder(){
        this.headers = new HashMap<String, String>();
        this.queryParams = new HashMap<String, String>();
    }


    public CanonicalRequestBuilder endpoint(URL endpoint){
        this.endpoint = endpoint;
        return this;
    }


    public CanonicalRequestBuilder method(String method){
        this.method = method;
        return this;
    }


    public CanonicalRequestBuilder body(String body){
        this.body = body;
        return this;
    }


    public CanonicalRequestBuilder requestTimestamp(String requestTimestamp){
        this.requestTimestamp = requestTimestamp;
        return this;
    }


    public CanonicalRequestBuilder headers(Map<String, String> headers){
        this.headers = headers;
        return this;
    }


    public CanonicalRequestBuilder addHeader(String name, String value){
        this.headers.put(name, value);
        return this;
    }


    public CanonicalRequestBuilder queryParams(Map<String, String> queryParams){
        this.queryParams = queryParams;
        return this;
    }


    public CanonicalRequestBuilder addQueryParam(String name, String value){
        this.queryParams.put(name, value);
        return this;
    }


    public String build() throws NoSuchAlgorithmException, UnsupportedEncodingException{
        StringBuilder builder = new StringBuilder();

        /* Append HTTP verb and request timestamp */
        builder.append(method).append("\n").append(requestTimestamp).append("\n");

        String canonicalizedResourcePath = this.getCanonicalizedResourcePath();
        String canonicalizedQueryParameters = this.getCanonicalizedQueryParameters();
        String canonicalizedHeaders = this.getCanonicalizedHeaders();
        String canonicalizedHeaderNames = this.getCanonicalizedHeaderNames();

        /* Append canonicalized resource path, query parameters */
        builder.append(canonicalizedResourcePath).append("\n")
               .append(canonicalizedQueryParameters).append("\n")
               .append(canonicalizedHeaders).append("\n")
               .append(canonicalizedHeaderNames).append("\n");

        if (bodyIsNull()){
            this.body = "";
        }

        hashBody(builder);
        
        return builder.toString();
    }


    private void hashBody(StringBuilder builder) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(this.body.getBytes("UTF-8"));

        String hashedBodyInHex = CryptoUtils.toHex(digest.digest());
        builder.append(hashedBodyInHex);
    }


    public String getCanonicalizedHeaderNames(){
        List<String> sortedHeaders = getSortedHeaders();

        StringBuilder builder = new StringBuilder();
        for (String header : sortedHeaders){
            builder.append(header.toLowerCase()).append(";");
        }

        return builder.deleteCharAt(builder.length() - 1).toString();
    }


    private String getCanonicalizedHeaders(){
        List<String> sortedHeaders = getSortedHeaders();

        StringBuilder builder = new StringBuilder();
        for (String headerName : sortedHeaders){
            String headerValue = headers.get(headerName);
            builder.append(headerName.toLowerCase())
                   .append(":")
                   .append(headerValue)
                   .append("\n");
        }

        return builder.deleteCharAt(builder.length() - 1).toString();
    }


    private List<String> getSortedHeaders(){
        List<String> sortedHeaders = new ArrayList<String>();
        sortedHeaders.addAll(headers.keySet());

        Collections.sort(sortedHeaders, String.CASE_INSENSITIVE_ORDER);

        return sortedHeaders;
    }


    private String getCanonicalizedQueryParameters() throws UnsupportedEncodingException{
        if (!hasQueryParameters()){
            return "";
        }

        SortedMap<String, String> sortedParameters = new TreeMap<String, String>();
        for (String key : queryParams.keySet()){
            String value = queryParams.get(key);

            String encondedKey = urlEncode(key, false);
            String encondedValue = urlEncode(value, false);

            sortedParameters.put(encondedKey, encondedValue);
        }

        StringBuilder builder = new StringBuilder();
        for (String encodedKey : sortedParameters.keySet()){
            String encodedValue = sortedParameters.get(encodedKey);
            builder.append(encodedKey)
                   .append("=")
                   .append(encodedValue)
                   .append("&");
        }

        return builder.deleteCharAt(builder.length() - 1).toString();
    }


    private boolean hasQueryParameters(){
        return this.queryParams != null && !queryParams.isEmpty();
    }


    private String getCanonicalizedResourcePath() throws UnsupportedEncodingException{
        String defaultCanonicalizedResourcePath = "/";

        if (endpoint != null){
            String path = endpoint.getPath();
            if (path != null && !path.isEmpty()){
                String encodedPath = this.urlEncode(path, true);
                if (encodedPath.startsWith("/")){
                    return encodedPath;
                }else{
                    return defaultCanonicalizedResourcePath.concat(encodedPath);
                }
            }
        }

        return defaultCanonicalizedResourcePath;
    }


    private String urlEncode(String url, boolean keepSlash) throws UnsupportedEncodingException{
        String encoded = URLEncoder.encode(url, "UTF-8");

        if (keepSlash){
            encoded = encoded.replace("%2F", "/");
        }

        return encoded;
    }


    private boolean bodyIsNull(){
        return this.body == null;
    }
}