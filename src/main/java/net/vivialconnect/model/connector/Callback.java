package net.vivialconnect.model.connector;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import net.vivialconnect.model.VivialConnectResource;
import net.vivialconnect.model.enums.CallbackMethod;
import net.vivialconnect.model.error.BadRequestException;
import net.vivialconnect.model.error.ServerErrorException;
import net.vivialconnect.model.error.ApiRequestException;
import net.vivialconnect.model.error.ForbiddenAccessException;
import net.vivialconnect.model.error.UnauthorizedAccessException;

/**
 * The CALLBACKS resource allows you to set callback URLs for message status, incoming messages,
 * and incoming message fallback. When creating or editing a callback, you must provide a message_type and event_type.
 * e.g. ‘text’ and ‘incoming’
 */
@JsonRootName("callback")
public class Callback extends VivialConnectResource {

    private static final long serialVersionUID = 6618863185770282392L;

    /**
     * Creation date (UTC) of the callback in ISO 8601 format
     */
    @JsonProperty("date_created")
    private Date dateCreated;

    /**
     * Last modification date (UTC) of the callback in ISO 8601 format
     */
    @JsonProperty("date_modified")
    private Date dateModified;

    /**
     * Message type this callback applies to
     */
    @JsonProperty("message_type")
    private String messageType;

    /**
     * Event type this callback applies to
     */
    @JsonProperty("event_type")
    private String eventType;

    /**
     * The URL that will receive callback request
     */
    @JsonProperty
    private String url;

    /**
     * The HTTP method which will be used for this callback
     */
    @JsonProperty
    private CallbackMethod method;

    /**
     * Using the connector ID returns the callbacks associated to the connector
     *
     * @param connectorId connector ID value
     * @return a Connector instance with callbacks
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params and/or payload  are not valid
     * @throws UnauthorizedAccessException if any of the auth properties account ID, API Key and/or API secret are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     * */
    public static ConnectorWithCallbacks getCallbacks(int connectorId) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        return request(RequestMethod.GET, classURLWithSuffix(Connector.class, String.format("%d/callbacks", connectorId)), null, null, Connector.class);
    }

    /**
     * Creation date of the callback
     *
     * @return creation date value
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * Set the creation date of the callback
     *
     * @param dateCreated callback creation date
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Last modification date of the callback
     *
     * @return last modification date of the callback
     */
    public Date getDateModified() {
        return dateModified;
    }

    /**
     * Set the last modification date of the callback
     *
     * @param dateModified last modification date value
     */
    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    /**
     * Message type for this callback
     *
     * @return callback's message type value
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     * Set message type for the callback
     *
     * @param messageType callback's message type value
     */
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    /**
     * Event type for this callback. Can be ‘incoming’, ‘incoming_fallback’, or ‘status’
     *
     * @return Callback's event type
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * Set callback's message type
     *
     * @param eventType callback's event type value
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    /**
     * The URL that will receive callback request
     *
     * @return callback URL value
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the callback URL value
     *
     * @param url callback URL value
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * The HTTP method used for this callback. Required to be ‘POST’ for event_type ‘status’.
     *
     * @return callback's method
     */
    public CallbackMethod getMethod() {
        return method;
    }

    /**
     * Set callback's HTTP method
     *
     * @param method callback's HTTP method
     */
    public void setMethod(CallbackMethod method) {
        this.method = method;
    }
}