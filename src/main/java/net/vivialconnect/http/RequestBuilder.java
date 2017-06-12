package net.vivialconnect.http;

import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

public class RequestBuilder{
    
    private WebResource resource;
    private Object entity;
    private MediaType type = null; /* MediaType.APPLICATION_JSON_TYPE; */
    private List<MediaType> accept = new ArrayList<MediaType>();
    private MultivaluedMap<String, String> queryParams;
    private MultivaluedMap<String, String> formData;
    private Map<String, Object> headers;
    private List<Cookie> cookies;

    
    public RequestBuilder(WebResource resource){
        this.resource = resource;
    }
    
    
    public RequestBuilder path(String path){
        this.resource = resource.path(path);
        return this;
    }
    
    
    public RequestBuilder queryParam(String name, String value){
        if(value != null){
            queryParams().add(name, value);
        }
        
        return this;
    }
    
    
    public RequestBuilder queryParam(String name, Integer intValue){
        if(intValue != null){
            String value = "" + intValue.intValue();
            queryParams().add(name, value);
        }
        
        return this;
    }
    
    
    public RequestBuilder queryParam(String name, Long longValue){
        if(longValue != null){
            String value = "" + longValue.longValue();
            queryParams().add(name, value);
        }
        
        return this;
    }
    
    
    public RequestBuilder queryParam(String name, Boolean value){
        if(value != null){
            queryParams().add(name, value.toString());
        }
        
        return this;
    }
    
    
    public RequestBuilder queryParam(String name, Collection<?> values){
        if(values != null && values.size() > 0){
            if(queryParams == null){
                queryParams = new MultivaluedMapImpl();
            }
            
            for(Object value : values){
                queryParams.add(name, value.toString());
            }
        }
        
        return this;
    }
    
    
    public RequestBuilder formData(String name, String value){
        if(value != null){
            formData().add(name, value);
        }
        
        return this;
    }
    
    
    public RequestBuilder formData(String name, Integer intValue){
        if(intValue != null){
            String value = "" + intValue.intValue();
            formData().add(name, value);
        }
        
        return this;
    }
    
    
    public RequestBuilder formData(String name, Long longValue){
        if(longValue != null){
            String value = "" + longValue.longValue();
            formData().add(name, value);
        }
        
        return this;
    }
    
    
    public RequestBuilder formData(String name, Boolean value){
        if(value != null){
            formData().add(name, value.toString());
        }
        
        return this;
    }
    
    
    public RequestBuilder formData(String name, Collection<?> values){
        if(values != null && values.size() > 0){
            if(formData == null){
                formData = new MultivaluedMapImpl();
            }
            
            for(Object value : values){
                formData.add(name, value.toString());
            }
        }
        
        return this;
    }
    
    
    public RequestBuilder entity(Object _entity){
        this.entity = _entity;
        return this;
    }
    
    
    public RequestBuilder entity(Object _entity, MediaType _type){
        entity(_entity);
        type(_type);
        return this;
    }
    
    
    public RequestBuilder entity(Object _entity, String _type){
        entity(_entity);
        type(_type);
        return this;
    }
    
    
    public RequestBuilder type(MediaType _type){
        this.type = _type;
        return this;
    }
    
    
    public RequestBuilder type(String _type){
        this.type = MediaType.valueOf(_type);
        return this;
    }
    
    
    public RequestBuilder accept(MediaType... types){
        for(MediaType t : types) {
            accept.add(t);
        }
        
        return this;
    }
    
    
    public RequestBuilder accept(String... types){
        for (String t : types) {
            accept.add(MediaType.valueOf(t));
        }
        
        return this;
    }
    
    
    public RequestBuilder cookie(Cookie cookie){
        if(cookies == null){
            cookies = new ArrayList<Cookie>();
        }
        
        cookies.add(cookie);
        return this;
    }
    
    
    public RequestBuilder header(String name, Object Value){
        if(headers == null){
            headers = new LinkedHashMap<String, Object>();
        }
        
        headers.put(name, Value);
        return this;
    }
    
    
    public <T> T options(Class<T> c) throws Exception{
        return handle(c,"OPTIONS");
    }
    
    
    public <T> T get(Class<T> c) throws Exception{
        return handle(c, "GET");
    }
    
    
    public <T> T getNoThrow(Class<T> c){
        try{
            return handle(c, "GET");
        }catch(Exception e){
            throw new IllegalStateException("Unexpected exception", e);
        }
    }
    
    
    public void put() throws Exception{
        voidHandle("PUT");
    }
    
    
    public void put(Object requestEntity) throws Exception{
        entity(requestEntity);
        voidHandle("PUT");
    }
    
    
    public <T> T put(Class<T> c) throws Exception{
        return handle(c, "PUT");
    }
    
    
    public <T> T put(Class<T> c, Object requestEntity) throws Exception{
        entity(requestEntity);
        return handle(c, "PUT");
    }
    
    
    public void post() throws Exception{
        voidHandle("POST");
    }
    
    
    public void post(Object requestEntity) throws Exception{
        entity(requestEntity);
        voidHandle("POST");
    }
    
    
    public <T> T post(Class<T> c) throws Exception{
        if(formData != null){
            entity(formData);
        }
        
        return handle(c, "POST");
    }
    
    
    public <T> T post(Class<T> c, Object requestEntity) throws Exception{
        entity(requestEntity);
        return handle(c, "POST");
    }
    
    
    public void delete() throws Exception{
        voidHandle("DELETE");
    }
    
    
    public <T> T delete(Class<T> c) throws Exception{
        return handle(c, "DELETE");
    }
    
    
    private <T> T handle(Class<T> c, String method) throws Exception{
        try{
            return generateBuilder().method(method, c);
        }catch(UniformInterfaceException uie){
            throw new Exception(uie);
        }
    }
    
    
    private void voidHandle(String method) throws Exception{
        try{
            generateBuilder().method(method);
        }catch(UniformInterfaceException uie){
            throw new Exception(uie);
        }
    }
    
    
    private WebResource.Builder generateBuilder()throws Exception{
        WebResource r = resource;
        if(queryParams != null){
            r = r.queryParams(queryParams);
        }
        
        WebResource.Builder builder = r.getRequestBuilder();
        
        if(entity != null){
            builder.entity(entity);
        }
        
        if(type != null){
            builder.type(type);
        }
        
        if(accept != null && accept.size() > 0){
            MediaType[] types = new MediaType[accept.size()];
            for (int i = 0; i < types.length; i++) {
                types[i] = accept.get(i);
            }
            
            builder.accept(types);
        }else{
            builder.accept(MediaType.APPLICATION_JSON_TYPE);
        }
        
        if(headers != null){
            for(Map.Entry<String, Object> header : headers.entrySet()){
                builder.header(header.getKey(), header.getValue());
            }
        }
        
        if(cookies != null){
            for (Cookie cookie : cookies) {
                builder.cookie(cookie);
            }
        }
        
        return builder;
    }
    
    
    private MultivaluedMap<String, String> queryParams(){
        if(queryParams == null){
            queryParams = new MultivaluedMapImpl();
        }
        
        return queryParams;
    }
    
    
    private MultivaluedMap<String, String> formData(){
        if(formData == null){
            formData = new MultivaluedMapImpl();
        }
        
        return formData;
    }
}