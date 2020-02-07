package net.vivialconnect.model.message;

import net.vivialconnect.model.VivialConnectResource;
import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.format.JsonBodyBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.vivialconnect.model.error.BadRequestException;
import net.vivialconnect.model.error.ServerErrorException;
import net.vivialconnect.model.error.ApiRequestException;
import net.vivialconnect.model.error.ForbiddenAccessException;
import net.vivialconnect.model.error.UnauthorizedAccessException;

/**
 * Bulk requests allow you to enqueue a large number of messages using only a few requests.
 * A single local phone number can only send one message per second.
 * To achieve greater throughput, use a Toll Free number or a Short Code with bulk send requests.
 * <p>
 * For more info, visit <a href="https://dashboard.vivialconnect.net/docs/api/messages.html#accounts-account-id-messages-bulk-json">Bulk Message</a>
 */
public class BulkMessage extends VivialConnectResource {

    static {
        classesWithoutRootValue.add(BulkInfo.class);
        classesWithoutRootValue.add(BulkInfoCollection.class);
    }

    /**
     * For inbound messages, the external phone number that sent the text message. Uses E.164 format (+country code +phone number). For US, the format will be +1xxxyyyzzzz.
     * For outbound messages, the associated phone number in your account that sent the text message.
     */
    private String fromNumber;

    /**
     * The id of the Connector to use to send the message with
     */
    private Integer connectorId;

    /**
     * Text body of the text message.
     */
    private String body;

    /**
     * List of destination phone numbers for the text message in E.164 format: +1xxxyyyzzzz.
     * Maximum list size is 100 numbers. Duplicates will be discarded.
     */
    private List<String> toNumbers;

    /**
     * URLs of the media you wish to send out with the message.
     */
    private List<String> mediaUrls;

    /**
     * For inbound messages, the external phone number that sent the text message. Uses E.164 format (+country code +phone number). For US, the format will be +1xxxyyyzzzz.
     * For outbound messages, the associated phone number in your account that sent the text message.
     *
     * @return from number value
     */
    public String getFromNumber() {
        return fromNumber;
    }

    /**
     * The id of the Connector to use to send the message with
     *
     * @return connector ID value
     */
    public Integer getConnectorId() {
        return connectorId;
    }

    /**
     * List of destination phone numbers for the text message in E.164 format: +1xxxyyyzzzz.
     * Maximum list size is 100 numbers. Duplicates will be discarded.
     *
     * @return list of destination numbers
     */
    public List<String> getToNumbers() {
        return toNumbers;
    }

    /**
     * URLs of the media you wish to send out with the message.
     *
     * @return list of media URLs
     */
    public List<String> getMediaUrls() {
        return mediaUrls;
    }

    /**
     * Text body of the message.
     *
     * @return message's body
     */
    public String getBody() {
        return body;
    }

    /**
     * Set the from number of the message
     *
     * @param fromNumber from number value
     */
    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
    }

    /**
     * Set connector ID of the message
     *
     * @param connectorId connector ID value
     */
    public void setConnectorId(Integer connectorId) {
        this.connectorId = connectorId;
    }

    /**
     * Set the message body content
     *
     * @param body message body content
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Set list of destination phone numbers
     *
     * @param toNumbers destination phone numbers
     */
    public void setToNumbers(List<String> toNumbers) {
        this.toNumbers = toNumbers;
    }

    /**
     * Set list of media URLs to send
     *
     * @param mediaUrls list of media URLs
     */
    public void setMediaUrls(List<String> mediaUrls) {
        this.mediaUrls = mediaUrls;
    }

    /**
     * Create the JSON payload for a send a bulk of messages using the properties provided.
     *
     * @return Structured JSON string with the data for send a bulk of messages.
     */
    private String createJson() {

        JsonBodyBuilder builder = JsonBodyBuilder.emptyJson();

        return builder.addParamPair("from_number", this.fromNumber)
                .addParamPair("body", body)
                .addParamPair("connector_id", connectorId)
                .addParamPair("to_numbers", toNumbers)
                .addParamPair("media_urls", mediaUrls)
                .build();
    }

    /**
     * Retrieve the messages sent in a bulk.
     *
     * @param bulkId ID of an existing bulk.
     * @return List of messages sent in a bulk.
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    public static List<Message> getBulk(String bulkId) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        String bulkIdPath = String.format("bulk/%s", bulkId);
        return request(RequestMethod.GET, classURLWithSuffix(Message.class, bulkIdPath), null, null, MessageCollection.class).getMessages();
    }

    /**
     * Returns a collection of bulk sent. This method returns the first of multiples pages.
     *
     * @return a BulkInfoCollection that contains: list of bulk sent,count of elements retrieved, number of pages, previous and next page.
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    public static BulkInfoCollection getBulksCreated() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        return getBulksCreated(1);
    }

    /**
     * Returns a collection of bulk sent. This method returns the "N" page of multiple pages.
     *
     * @return a BulkInfoCollection that contains: list of bulk sent,count of elements retrieved, number of pages, previous and next page.
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    public static BulkInfoCollection getBulksCreated(int page) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("page", String.valueOf(page));

        return request(RequestMethod.GET, classURLWithSuffix(Message.class, "bulk"), null, queryParams, BulkInfoCollection.class);
    }

    /**
     * Send a bulk of messages.
     *
     * @return a BulkInfo with the ID of the bulk.
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     * */
    public BulkInfo send() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {

        if (toNumbers == null || toNumbers.isEmpty()) {
            throw new IllegalStateException("The list of numbers cannot be null or empty");
        }

        String payload = createJson();

        return request(RequestMethod.POST, classURLWithSuffix(Message.class, "bulk"), payload, null, BulkInfo.class);
    }

}
