package net.vivialconnect.model.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import net.vivialconnect.model.ResourceCount;
import net.vivialconnect.model.VivialConnectResource;
import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.format.JsonBodyBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Sending messages is the main feature provide by the Vivial Connect API. This class represents the Message entity and provide all the
 * properties and methods needed for send and retrieve messages from your account.
 * <p>
 * The API uses two types of messages:
 *
 * <ul>
 *     <li>SMS messages - basic text messages</li>
 *     <li>MMS messages - text messages with media attachments</li>
 * </ul>
 * <p>
 * For more info, visit: https://dashboard.vivialconnect.net/docs/api/messages.html
 *
 * @see Number
 */
@JsonRootName(value = "message")
public class Message extends VivialConnectResource {

    private static final long serialVersionUID = 5181807107956389186L;

    /**
     * Unique identifier of the text message object
     */
    @JsonProperty
    private int id;

    /**
     * Creation date (UTC) of the text message in ISO 8601 format
     */
    @JsonProperty("date_created")
    private Date dateCreated;

    /**
     * Last modification date (UTC) of the text message in ISO 8601 format
     */
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
     * <p>
     * For inbound messages, the external phone number that sent the text
     * message. Uses E.164 format (+country code +phone number). For US, the
     * format will be +1xxxyyyzzzz
     * <p>
     * For outbound messages, the associated phone number in your account that
     * sent the text message
     */
    @JsonProperty("from_number")
    private String fromNumber;

    /**
     * The id of the Connector to use to send the message with
     */
    @JsonProperty("connector_id")
    private int connectorId;

    /**
     * One of the following in ISO 8601 format:
     * <p>
     * For inbound messages, the UTC timestamp the text message was received
     * <p>
     * For outbound messages, the UTC timestamp the text message was sent
     */
    @JsonProperty
    private Date sent;

    /**
     * Number of media attachments for the text message
     */
    @JsonProperty("num_media")
    private int numMedia;

    /**
     * Number of segments that make up the message
     */
    @JsonProperty("num_segments")
    private int numSegments;

    /**
     * Text body of the text message. Max. length: 1,600 characters
     */
    @JsonProperty
    private String body;

    /**
     * Status of the message
     */
    @JsonProperty
    private String status;

    /**
     * Error code, if any, for the message
     */
    @JsonProperty("error_code")
    private String errorCode;

    /**
     * Error code message for error_code as it is displayed to users
     */
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

    /**
     * ID of the bulk to which this message belongs.
     */
    @JsonProperty("bulk_id")
    private String bulkId;

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
     * @see #setBody(String)
     * @see #setFromNumber(String)
     * @see #setToNumber(String)
     * @see #setConnectorId(int)
     * @see #addMediaUrl(String)
     * @see #setMediaUrls(List)
     */
    public Message send() throws VivialConnectException {
        Message sentMessage = request(RequestMethod.POST, classURL(Message.class), jsonBody(), null, Message.class);
        updateObjectState(sentMessage);
        return this;
    }


    private void updateObjectState(Message sentMessage) {
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
        this.bulkId = sentMessage.getBulkId();
    }


    private String jsonBody() {
        JsonBodyBuilder builder = JsonBodyBuilder.forClass(Message.class);
        if (hasMediaUrls()) {
            builder.addParamPair("media_urls", mediaUrls);
        }

        if (connectorId > 0) {
            builder.addParamPair("connector_id", connectorId);
        }

        return builder.addParamPair("from_number", this.fromNumber)
                .addParamPair("to_number", this.toNumber)
                .addParamPair("body", this.body)
                .build();
    }

    /**
     * Tells if the message has media URLs
     *
     * @return boolean value (True/False)
     */
    private boolean hasMediaUrls() {
        return this.mediaUrls != null && !this.mediaUrls.isEmpty();
    }

    /**
     * Retrieves a single message given an id.
     *
     * @param messageId the id of the message to look up
     * @return the Message found, or null if not found
     * @throws VivialConnectException if there is an API-level error
     * @see #getMessages()
     * @see #getMessages(Map)
     */
    public static Message getMessageById(int messageId) throws VivialConnectException {
        return request(RequestMethod.GET, classURLWithSuffix(Message.class, String.valueOf(messageId)), null, null, Message.class);
    }

    /**
     * Gets all the messages associated with the current account. If there are none, this method will return an empty { @link List }
     *
     * @return a list of messages
     * @throws VivialConnectException if there is an API-level error
     * @see #getMessageById(int)
     * @see #getMessages(Map)
     */
    public static List<Message> getMessages() throws VivialConnectException {
        return getMessages(null);
    }

    /**
     * Lists and filters the messages associated with the current account. If there are none, the method will return an empty {@link List}
     *
     * @param queryParameters a map of {@link String } key-value pairs used to filter results, possible values are:
     *                        <p>
     *                        <code>page</code> – Page number within the returned list of text messages. Default value: 1.
     *                        <p>
     *                        <code>limit</code> – Number of results to return per page. Default value: 50. Maximum value: 150.
     * @return a list of messages
     * @throws VivialConnectException if there is an API-level error
     * @see #getMessages()
     * @see #getMessageById(int)
     */
    public static List<Message> getMessages(Map<String, String> queryParameters) throws VivialConnectException {
        return request(RequestMethod.GET, classURL(Message.class), null, queryParameters, MessageCollection.class).getMessages();
    }

    /**
     * Total number of messages in the account. If there are none, this method will return <code>0</code>.
     *
     * @return message count
     * @throws VivialConnectException if there is an API-level error
     */
    public static int count() throws VivialConnectException {
        return request(RequestMethod.GET, classURLWithSuffix(Message.class, "count"), null, null, ResourceCount.class).getCount();
    }

    /**
     * Retrieves this message's media attachments. If the message has none, it will return an empty {@link List}.
     *
     * @return an attachment list
     * @throws VivialConnectException if there is an API-level error
     */
    public List<Attachment> getAttachments() throws VivialConnectException {
        return request(RequestMethod.GET, classURLWithSuffix(Message.class, String.format("%d/attachments", this.getId())), null, null, AttachmentCollection.class).getAttachments();
    }

    /**
     * Redacts the text message by replacing the message body text with an empty value.
     *
     * @return this Message instance with the body text redacted
     * @throws VivialConnectException if there is an API-level error
     */
    public Message redact() throws VivialConnectException {
        Message redactedMessage = request(RequestMethod.PUT, classURLWithSuffix(Message.class, String.valueOf(this.getId())), jsonBodyEmpty(), null, Message.class);
        updateObjectState(redactedMessage);

        return this;
    }


    private String jsonBodyEmpty() {
        return JsonBodyBuilder.forClass(Message.class)
                .addParamPair("id", getId())
                .addParamPair("body", "")
                .build();
    }

    /**
     * Unique identifier of the text message object.
     *
     * @return unique object ID of the message
     */
    public int getId() {
        return id;
    }

    /**
     * Set unique identifier of the text message object.
     *
     * @param id object's ID value
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Creation date of the message
     *
     * @return creation date value
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * Set creation date of the message
     *
     * @param dateCreated creation date value
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Last modification date of the message
     *
     * @return last modification date of the message
     */
    public Date getDateModified() {
        return dateModified;
    }

    /**
     * Set last modification date of the message
     *
     * @param dateModified last modification date of the message
     */
    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    /**
     * Unique identifier of the account associated with the text message.
     *
     * @return account ID value
     */
    public int getAccountId() {
        return accountId;
    }

    /**
     * Set unique identifier of the account associated with the text message.
     *
     * @param accountId account ID value
     */
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    /**
     * Master Account ID
     *
     * @return master account ID value
     */
    public int getMasterAccountId() {
        return masterAccountId;
    }

    /**
     * Set master account ID
     *
     * @param masterAccountId
     */
    public void setMasterAccountId(int masterAccountId) {
        this.masterAccountId = masterAccountId;
    }

    /**
     * String identifying the type of inbound or outbound text message.
     *
     * @return message type value
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     * Set the message type
     *
     * @param messageType message type value
     */
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    /**
     * Inbound/outbound direction of the text message, and if outbound, the nature of the text message initiation.
     *
     * @return message's direction value
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Set the message's direction
     *
     * @param direction message's direction value
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * Phone number that received the text message.
     *
     * @return destination phone number value
     */
    public String getToNumber() {
        return toNumber;
    }

    /**
     * Set the destination phone number to where the text message will be sent.
     *
     * @param toNumber - Destination phone number for the text message in E.164 format (+country code +phone number)
     */
    public void setToNumber(String toNumber) {
        this.toNumber = toNumber;
    }

    /**
     * One of the following:
     * <p>
     * For inbound messages, the external phone number that sent the text
     * message. Uses E.164 format (+country code +phone number). For US, the
     * format will be +1xxxyyyzzzz
     * <p>
     * For outbound messages, the associated phone number in your account that
     * sent the text message
     *
     * @return from number value
     */
    public String getFromNumber() {
        return fromNumber;
    }

    /**
     * Sets the origin phone number for the text message.
     *
     * @param fromNumber Origination of the text message using the associated phone number
     *                   you specify in E.164 format (+country code +phone number). It must match an associated number
     *                   you have purchased for your account.
     */
    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
    }

    /**
     * The id of the Connector to use to send the message with.
     *
     * @return connector ID value
     */
    public int getConnectorId() {
        return connectorId;
    }

    /**
     * Set the connector ID for send the message. The  connector ID must exists in the API.
     *
     * @param connectorId connector ID value.
     */
    public void setConnectorId(int connectorId) {
        this.connectorId = connectorId;
    }

    /**
     * One of the following in ISO 8601 format:
     * <p>
     * For inbound messages, the UTC timestamp the text message was received
     * <p>
     * For outbound messages, the UTC timestamp the text message was sent
     *
     * @return Sent date value
     */
    public Date getSent() {
        return sent;
    }

    /**
     * Set the sent date for the message
     *
     * @param sent sent date value
     */
    public void setSent(Date sent) {
        this.sent = sent;
    }

    /**
     * Number of media attachments for the text message.
     *
     * @return number of media attachments for the message.
     */
    public int getNumMedia() {
        return numMedia;
    }

    /**
     * Set the number of media attachments for the message.
     *
     * @param numMedia num media value
     */
    public void setNumMedia(int numMedia) {
        this.numMedia = numMedia;
    }

    /**
     * Number of segments that make up the message
     *
     * @return number of segments value
     */
    public int getNumSegments() {
        return numSegments;
    }

    /**
     * Set number of segments of the message
     *
     * @param numSegments number of segment value
     */
    public void setNumSegments(int numSegments) {
        this.numSegments = numSegments;
    }

    /**
     * Text body of the text message
     *
     * @return message's body value
     */
    public String getBody() {
        return body;
    }

    /**
     * Set the body of the message to be send
     * <p>
     * This supports text, unicode characters, emojis or chinese letters
     *
     * @param body the body of the message
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Status of the message
     *
     * @return stus of the message
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set status of the message
     *
     * @param status status of the message
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Error code, if any, for the message.
     *
     * @return error code of the message
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Set error code of the message
     *
     * @param errorCode error code of the message
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Description of the error code
     *
     * @return description of the error code
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Set description of an error code
     *
     * @param errorMessage error's description value
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Amount billed for the message, in the currency associated with the account.
     *
     * @return message's price value
     */
    public int getPrice() {
        return price;
    }

    /**
     * Set the message price.
     *
     * @param price message's price value
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Currency in which price is measured in ISO 4127 format.
     *
     * @return currency of the price
     */
    public String getPriceCurrency() {
        return priceCurrency;
    }

    /**
     * Set the message currency value
     *
     * @param priceCurrency currency value
     */
    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
    }

    /**
     * Add a media URL to the message
     *
     * @param mediaUrl media URL
     * @return this instance of the message with the URL added
     */
    public Message addMediaUrl(String mediaUrl) {
        if (mediaUrls == null) {
            mediaUrls = new ArrayList<String>();
        }

        mediaUrls.add(mediaUrl);

        return this;
    }

    /**
     * List of media URLs for this message.
     *
     * @return list of media URLs
     */
    public List<String> getMediaUrls() {
        return mediaUrls;
    }

    /**
     * Set media URLs for the message
     *
     * @param mediaUrls list of media URLs
     */
    public void setMediaUrls(List<String> mediaUrls) {
        this.mediaUrls = mediaUrls;
    }

    /**
     * Bulk ID if the message belongs to a bulk send
     *
     * @return Bulk ID
     */
    public String getBulkId() {
        return bulkId;
    }

    /**
     * Set the bulk ID for this message
     *
     * @param bulkId bull ID for this message
     */
    public void setBulkId(String bulkId) {
        this.bulkId = bulkId;
    }

}
