package net.vivialconnect.http;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;
import java.net.URI;
import javax.ws.rs.core.UriBuilder;

public class VivialRESTClient{

    private URI baseUri;
    private ClientConfig config;

    
    public VivialRESTClient(String path){
        this(path, new DefaultClientConfig());
    }
    
    
    public VivialRESTClient(String path, ClientConfig config){
        this.config = config;
        this.baseUri = UriBuilder.fromPath(path).build();
    }
    
    
    public RequestBuilder request(){
        Client client = Client.create(config);
        client.addFilter(new LoggingFilter(System.out));
        
        WebResource r = client.resource(baseUri);
        return new RequestBuilder(r);
    }
}