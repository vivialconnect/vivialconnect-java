package net.vivialconnect.model.message;

import net.vivialconnect.model.VivialConnectResource;
import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.format.JsonBodyBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BulkMessage extends VivialConnectResource {

    static {
        classesWithoutRootValue.add(BulkInfo.class);
        classesWithoutRootValue.add(BulkInfoCollection.class);
    }

    private String fromNumber;

    private Integer connectorId;

    private String body;

    private List<String> toNumbers;

    private List<String> mediaUrls;

    public String getFromNumber() {
        return fromNumber;
    }

    public Integer getConnectorId() {
        return connectorId;
    }

    public List<String> getToNumbers() {
        return toNumbers;
    }

    public List<String> getMediaUrls() {
        return mediaUrls;
    }

    public String getBody() {
        return body;
    }

    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
    }

    public void setConnectorId(Integer connectorId) {
        this.connectorId = connectorId;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setToNumbers(List<String> toNumbers) {
        this.toNumbers = toNumbers;
    }

    public void setMediaUrls(List<String> mediaUrls) {
        this.mediaUrls = mediaUrls;
    }

    /**
     * Create the JSON payload for a send a bulk of messages using the properties provided.
     * @return Structured JSON string with the data for send a bulk of messages.
     */
    private String createJson(){

        JsonBodyBuilder builder = JsonBodyBuilder.emptyJson();

        return builder.addParamPair("from_number", this.fromNumber)
                .addParamPair("body", body)
                .addParamPair("connector_id",connectorId)
                .addParamPair("to_numbers", toNumbers)
                .addParamPair("media_urls",mediaUrls)
                .build();
    }

    /**
     * Retrieve the messages sent in a bulk.
     * @param bulkId ID of an existing bulk.
     * @return List of messages sent in a bulk.
     * @throws VivialConnectException
     */
    public static List<Message> getBulk(String bulkId) throws VivialConnectException{
        String bulkIdPath = String.format("bulk/%s",bulkId);
        return request(RequestMethod.GET, classURLWithSuffix(Message.class, bulkIdPath), null, null, MessageCollection.class).getMessages();
    }

    /**
     * Returns a collection of bulk sent. This method returns the first of multiples pages.
     * @return a BulkInfoCollection that contains: list of bulk sent,count of elements retrieved, number of pages, previous and next page.
     * @throws VivialConnectException
     */
    public static BulkInfoCollection getBulksCreated() throws VivialConnectException{
        return getBulksCreated(1);
    }

    /**
     * Returns a collection of bulk sent. This method returns the "N" page of multiple pages.
     * @return a BulkInfoCollection that contains: list of bulk sent,count of elements retrieved, number of pages, previous and next page.
     * @throws VivialConnectException
     */
    public static BulkInfoCollection getBulksCreated(int page) throws VivialConnectException{
        Map<String,String> queryParams = new HashMap<String,String>();
        queryParams.put("page", String.valueOf(page));

        return request(RequestMethod.GET, classURLWithSuffix(Message.class, "bulk"), null, queryParams, BulkInfoCollection.class);
    }

    /**
     * Send a bulk of messages.
     * @return a BulkInfo with the ID of the bulk.
     * @throws VivialConnectException through this exception if toNumbers property is null or empty.
     */
    public BulkInfo send() throws VivialConnectException {

        if(toNumbers == null || toNumbers.isEmpty()){
            throw new VivialConnectException("The list of numbers cannot be null or empty",null);
        }

        String payload = createJson();

        return request(RequestMethod.POST, classURLWithSuffix(Message.class, "bulk"),payload, null, BulkInfo.class);
    }

}
