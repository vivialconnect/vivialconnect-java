package net.vivialconnect.model.connector;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import net.vivialconnect.model.ResourceCount;
import net.vivialconnect.model.VivialConnectResource;
import net.vivialconnect.model.error.VivialConnectException;

@JsonRootName("callback")
public class Callback extends VivialConnectResource{

    private static final long serialVersionUID = 6618863185770282392L;

    /** Creation date (UTC) of the callback in ISO 8601 format */
    @JsonProperty("date_created")
    private Date dateCreated;

    /** Last modification date (UTC) of the callback in ISO 8601 format */
    @JsonProperty("date_modified")
    private Date dateModified;

    /** Message type this callback applies to */
    @JsonProperty("message_type")
    private String messageType;

    /** Event type this callback applies to */
    @JsonProperty("event_type")
    private String eventType;

    /** The URL that will receive callback request */
    @JsonProperty
    private String url;

    /** The HTTP method which will be used for this callback */
    @JsonProperty
    private String method;

    /* public enum EventType {

        INCOMING,
        INCOMING_FALLBACK,
        STATUS;

        public String toString()
        {
                return this.name().toLowerCase();
        }
    }


    public enum MessageType {

        TEXT,
        VOICE;

        public String toString()
        {
                return this.name().toLowerCase();
        }
    } */


    public static ConnectorWithCallbacks getCallbacks(int connectorId) throws VivialConnectException{
        return request(RequestMethod.GET, classURLWithSuffix(Connector.class, String.format("%d/callbacks", connectorId)), null, null, Connector.class);
    }


    public Date getDateCreated(){
        return dateCreated;
    }


    public void setDateCreated(Date dateCreated){
        this.dateCreated = dateCreated;
    }


    public Date getDateModified(){
        return dateModified;
    }


    public void setDateModified(Date dateModified){
        this.dateModified = dateModified;
    }


    public String getMessageType(){
        return messageType;
    }


    public void setMessageType(String messageType){
        this.messageType = messageType;
    }


    public String getEventType(){
        return eventType;
    }


    public void setEventType(String eventType){
        this.eventType = eventType;
    }


    public String getUrl(){
        return url;
    }


    public void setUrl(String url){
        this.url = url;
    }


    public String getMethod(){
        return method;
    }


    public void setMethod(String method){
        this.method = method;
    }
}