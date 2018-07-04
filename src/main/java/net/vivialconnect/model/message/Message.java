package net.vivialconnect.model.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import net.vivialconnect.model.ResourceCount;
import net.vivialconnect.model.VivialConnectResource;
import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.format.JsonBodyBuilder;
import net.vivialconnect.util.StringUtils;

@JsonRootName(value = "message")
public class Message extends VivialConnectResource{
	
    private static final long serialVersionUID = 5181807107956389186L;

    /** Unique identifier of the text message object */
    @JsonProperty
    private int id;

    /** Creation date (UTC) of the text message in ISO 8601 format */
    @JsonProperty("date_created")
    private Date dateCreated;

    /** Last modification date (UTC) of the text message in ISO 8601 format */
    @JsonProperty("date_modified")
    private Date dateModified;

    /**
     * Unique identifier of the account or subaccount associated with the text
     * message
     */
    @JsonProperty("account_id")
    private int accountId;

    /**
     * For subaccounts, the account_id of the subaccount’s parent (primary)
     * account
     */
    @JsonProperty("master_account_id")
    private int masterAccountId;

    /**
     * String identifying the type of inbound or outbound text message. Possible
     * values: local_sms, tollfree_sms, or local_mms
     */
    @JsonProperty("message_type")
    private String messageType;

    /**
     * Inbound/outbound direction of the text message, and if outbound, the
     * nature of the text message initiation
     */
    @JsonProperty
    private String direction;

    /**
     * Phone number that received the text message. Uses E.164 format (+country
     * code +phone number). For US, the format will be +1xxxyyyzzzz
     */
    @JsonProperty("to_number")
    private String toNumber;

    /**
     * One of the following:
     * 
     * For inbound messages, the external phone number that sent the text
     * message. Uses E.164 format (+country code +phone number). For US, the
     * format will be +1xxxyyyzzzz
     * 
     * For outbound messages, the associated phone number in your account that
     * sent the text message
     * 
     */
    @JsonProperty("from_number")
    private String fromNumber;

    /** The id of the Connector to use to send the message with */
    @JsonProperty("connector_id")
    private int connectorId;

    /**
     * One of the following in ISO 8601 format:
     * 
     * For inbound messages, the UTC timestamp the text message was received
     * 
     * For outbound messages, the UTC timestamp the text message was sent
     * 
     */
    @JsonProperty
    private Date sent;

    /** Number of media attachments for the text message */
    @JsonProperty("num_media")
    private int numMedia;

    /** Number of segments that make up the message */
    @JsonProperty("num_segments")
    private int numSegments;

    /** Text body of the text message. Max. length: 1,600 characters */
    @JsonProperty
    private String body;

    /** Status of the message */
    @JsonProperty
    private String status;

    /** Error code, if any, for the message */
    @JsonProperty("error_code")
    private String errorCode;

    /** Error code message for error_code as it is displayed to users */
    @JsonProperty("error_message")
    private String errorMessage;

    /**
     * Amount billed for the message, in the currency associated with the
     * account
     */
    @JsonProperty
    private int price;

    /**
     * Currency in which price is measured in ISO 4127 format. For US, the
     * currency will be USD
     */
    @JsonProperty("price_currency")
    private String priceCurrency;

    /**
     * URLs of the media you wish to send out with the message.
     */
    private List<String> mediaUrls;

    static {
        classesWithoutRootValue.add(MessageCollection.class);
        classesWithoutRootValue.add(AttachmentCollection.class);
    }
    
    /**
     * Sends this text message using the API.
     * <p>
     * If the <code>connector_id</code> property is set, the <code>from_number</code>
     * property will be ignored.
     * <p>
     * In order to send an MMS, be sure to add media attachments to this message
     * using the {@link #addMediaUrl(String)} and {@link #setMediaUrls(List)} methods.
     *
     * @return the message that was just sent
     * @throws VivialConnectException if there is an API-level error
     * 
     * @see #setBody(String)
     * @see #setFromNumber(String)
     * @see #setToNumber(String)
     * @see #setConnectorId(int)
     * @see #addMediaUrl(String)
     * @see #setMediaUrls(List)
     * 
     */
    public Message send() throws VivialConnectException{
        Message sentMessage = request(RequestMethod.POST, classURL(Message.class), jsonBody(), null, Message.class);
        updateObjectState(sentMessage);
        return this;
    }
    

    private void updateObjectState(Message sentMessage){
        this.id = sentMessage.getId();
        this.accountId = sentMessage.getAccountId();
        this.body = sentMessage.getBody();
        this.connectorId = sentMessage.getConnectorId();
        this.dateCreated = sentMessage.getDateCreated();
        this.dateModified = sentMessage.getDateModified();
        this.direction = sentMessage.getDirection();
        this.errorCode = sentMessage.getErrorCode();
        this.errorMessage = sentMessage.getErrorMessage();
        this.fromNumber = sentMessage.getFromNumber();
        this.toNumber = sentMessage.getToNumber();
        this.masterAccountId = sentMessage.getMasterAccountId();
        this.mediaUrls = sentMessage.getMediaUrls();
        this.messageType = sentMessage.getMessageType();
        this.numMedia = sentMessage.getNumMedia();
        this.numSegments = sentMessage.getNumSegments();
        this.price = sentMessage.getPrice();
        this.priceCurrency = sentMessage.getPriceCurrency();
        this.sent = sentMessage.getSent();
        this.status = sentMessage.getStatus();
    }
    
    
    private String jsonBody(){
        JsonBodyBuilder builder = JsonBodyBuilder.forClass(Message.class);
        if (hasMediaUrls()){
            builder.addParamPair("media_urls", mediaUrls);
        }

        if (connectorId > 0){
            builder.addParamPair("connector_id", connectorId);
        }

        return builder.addParamPair("from_number", this.fromNumber)
                      .addParamPair("to_number", this.toNumber)
                      .addParamPair("body", this.body)
                      .build();
    }


    private boolean hasMediaUrls(){
        return this.mediaUrls != null && !this.mediaUrls.isEmpty();
    }

    /**
     * Retrieves a single message given an id.
     * 
     * @param messageId the id of the message to look up
     * 
     * @return the Message found, or null if not found
     * @throws VivialConnectException if there is an API-level error
     * 
     * @see #getMessages()
     * @see #getMessages(Map)
     */
    public static Message getMessageById(int messageId) throws VivialConnectException{
        return request(RequestMethod.GET, classURLWithSuffix(Message.class, String.valueOf(messageId)), null, null, Message.class);
    }

    /**
     * Gets all the messages associated with the current account. If there are none, this method will return an empty { @link List }
     * 
     * @return a list of messages
     * @throws VivialConnectException if there is an API-level error
     * 
     * @see #getMessageById(int)
     * @see #getMessages(Map) 
     */
    public static List<Message> getMessages() throws VivialConnectException{
        return getMessages(null);
    }

    /**
     * Lists and filters the messages associated with the current account. If there are none, the method will return an empty {@link List}
     * 
     * @param queryParameters a map of {@link String } key-value pairs used to filter results, possible values are:
     * <p>
     * <code>page</code> – Page number within the returned list of text messages. Default value: 1.
     * <p>
     * <code>limit</code> – Number of results to return per page. Default value: 50. Maximum value: 150.
     * 
     * @return a list of messages
     * @throws VivialConnectException if there is an API-level error
     * 
     * @see #getMessages()
     * @see #getMessageById(int) 
     */
    public static List<Message> getMessages(Map<String, String> queryParameters) throws VivialConnectException{
        return request(RequestMethod.GET, classURL(Message.class), null, queryParameters, MessageCollection.class).getMessages();
    }

    /**
     * Total number of messages in the account. If there are none, this method will return <code>0</code>.
     * 
     * @return message count
     * @throws VivialConnectException if there is an API-level error
     *
     */
    public static int count() throws VivialConnectException{
        return request(RequestMethod.GET, classURLWithSuffix(Message.class, "count"), null, null, ResourceCount.class).getCount();
    }
    
    /**
     * Retrieves this message's media attachments. If the message has none, it will return an empty {@link List}.
     * 
     * @return an attachment list
     * @throws VivialConnectException if there is an API-level error
     *
     */
    public List<Attachment> getAttachments() throws VivialConnectException{
        return request(RequestMethod.GET, classURLWithSuffix(Message.class, String.format("%d/attachments", this.getId())), null, null, AttachmentCollection.class).getAttachments();
    }

    /**
     * Redacts the text message by replacing the message body text with an empty value.
     * 
     * @return this Message instance with the body text redacted
     * @throws VivialConnectException if there is an API-level error
     *
     */
    public Message redact() throws VivialConnectException{
        Message redactedMessage = request(RequestMethod.PUT, classURLWithSuffix(Message.class, String.valueOf(this.getId())), jsonBodyEmpty(), null, Message.class);
        updateObjectState(redactedMessage);
        
        return this;
    }


    private String jsonBodyEmpty(){
        return JsonBodyBuilder.forClass(Message.class)
                              .addParamPair("id", getId())
                              .addParamPair("body", "")
                              .build();
    }


    public int getId(){
        return id;
    }


    public void setId(int id){
        this.id = id;
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


    public int getAccountId(){
        return accountId;
    }


    public void setAccountId(int accountId){
        this.accountId = accountId;
    }


    public int getMasterAccountId(){
        return masterAccountId;
    }


    public void setMasterAccountId(int masterAccountId){
        this.masterAccountId = masterAccountId;
    }


    public String getMessageType(){
        return messageType;
    }


    public void setMessageType(String messageType){
        this.messageType = messageType;
    }


    public String getDirection(){
        return direction;
    }


    public void setDirection(String direction){
        this.direction = direction;
    }


    public String getToNumber(){
        return toNumber;
    }

    /**
     * Set the destination phone number to where the text message will be sent.
     *
     * @param toNumber - Destination phone number for the text message in E.164 format (+country code +phone number)
     */
    public void setToNumber(String toNumber){
        this.toNumber = toNumber;
    }

    public String getFromNumber(){
        return fromNumber;
    }

    /**
     * Sets the origin phone number for the text message.
     *
     * @param fromNumber Origination of the text message using the associated phone number
     *                             you specify in E.164 format (+country code +phone number). It must match an associated number
     *                             you have purchased for your account.
     */
    public void setFromNumber(String fromNumber){
        this.fromNumber = fromNumber;
    }


    public int getConnectorId(){
        return connectorId;
    }


    public void setConnectorId(int connectorId){
        this.connectorId = connectorId;
    }


    public Date getSent(){
        return sent;
    }


    public void setSent(Date sent){
        this.sent = sent;
    }


    public int getNumMedia(){
        return numMedia;
    }


    public void setNumMedia(int numMedia){
        this.numMedia = numMedia;
    }


    public int getNumSegments(){
        return numSegments;
    }


    public void setNumSegments(int numSegments){
        this.numSegments = numSegments;
    }


    public String getBody(){
        return body;
    }

    /**
     * Set the body of the message to be send
     * <p>
     * This supports text, unicode characters, emojis or chinese letters
     *
     * @param body the body of the message
     */
    public void setBody(String body){
        this.body = body;
    }


    public String getStatus(){
        return status;
    }


    public void setStatus(String status){
        this.status = status;
    }


    public String getErrorCode(){
        return errorCode;
    }


    public void setErrorCode(String errorCode){
        this.errorCode = errorCode;
    }


    public String getErrorMessage(){
        return errorMessage;
    }


    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }


    public int getPrice(){
        return price;
    }


    public void setPrice(int price){
        this.price = price;
    }


    public String getPriceCurrency(){
        return priceCurrency;
    }


    public void setPriceCurrency(String priceCurrency){
        this.priceCurrency = priceCurrency;
    }


    public Message addMediaUrl(String mediaUrl){
        if (mediaUrls == null){
            mediaUrls = new ArrayList<String>();
        }

        mediaUrls.add(mediaUrl);
        
        return this;
    }


    public List<String> getMediaUrls(){
        return mediaUrls;
    }


    public void setMediaUrls(List<String> mediaUrls){
        this.mediaUrls = mediaUrls;
    }
}
