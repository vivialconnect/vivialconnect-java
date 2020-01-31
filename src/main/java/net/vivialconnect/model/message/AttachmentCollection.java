package net.vivialconnect.model.message;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Wrapper class for map the collection of attachments returned by the API.
 */
public class AttachmentCollection {

    /**
     * Collection of attachments
     */
    @JsonProperty
    private List<Attachment> attachments;

    /**
     * Collection of attachments from a message
     *
     * @return collection of attachments
     */
    public List<Attachment> getAttachments() {
        return attachments;
    }

    /**
     * Set a collection of attachments
     *
     * @param attachments collection of attachments
     */
    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}