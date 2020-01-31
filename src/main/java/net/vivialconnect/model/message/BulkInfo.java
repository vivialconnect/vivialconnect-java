package net.vivialconnect.model.message;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public class BulkInfo {

    /**
     * Bulk ID
     */
    @JsonProperty(value = "bulk_id")
    private String bulkId;

    /**
     * Total message sent in this bulk send
     */
    @JsonProperty(value = "total_message")
    private int totalMessage;

    /**
     * Creation date of the bulk send
     */
    @JsonProperty(value = "date_created")
    private Date dateCreated;

    /**
     * Quantity of  message processed successfully
     */
    @JsonProperty(value = "processed")
    private int processed;

    /**
     * Quantity of messages failed
     */
    @JsonProperty(value = "errors")
    private int errors;

    /**
     * ID of a bulk send
     *
     * @return bulk ID value
     */
    public String getBulkId() {
        return bulkId;
    }

    /**
     * Set bulk ID value
     *
     * @param bulkId bulk ID value
     */
    public void setBulkId(String bulkId) {
        this.bulkId = bulkId;
    }

    /**
     * Bulk's total messages created
     *
     * @return total messages value
     */
    public int getTotalMessage() {
        return totalMessage;
    }

    /**
     * Set total message value
     *
     * @param totalMessage total message value
     */
    public void setTotalMessage(int totalMessage) {
        this.totalMessage = totalMessage;
    }

    /**
     * Creation date of this bulk send
     *
     * @return creation date of the bulk send
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * Set the creation date of the bulk send
     *
     * @param dateCreated creation date
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Quantity of messages processed successfully.
     *
     * @return quantity of messages processed
     */
    public int getProcessed() {
        return processed;
    }

    /**
     * Set quantity of messages processed successfully in the bulk send
     *
     * @param processed processed message value
     */
    public void setProcessed(int processed) {
        this.processed = processed;
    }

    /**
     * Quantity of messages failed in the bulk send
     *
     * @return messages failed
     */
    public int getErrors() {
        return errors;
    }

    /**
     * Set quantity of messages failed in the bulk send
     *
     * @param errors quantity of messages failed
     */
    public void setErrors(int errors) {
        this.errors = errors;
    }

}