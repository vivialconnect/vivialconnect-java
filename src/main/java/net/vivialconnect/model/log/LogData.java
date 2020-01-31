package net.vivialconnect.model.log;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Log's entity for message status entries
 */
public class LogData {

    /**
     * Reciever's phone number of the message
     */
    @JsonProperty
    private String receiver;

    /**
     * Sender's phone number of the message
     */
    @JsonProperty
    private String sender;

    /**
     * Content of the message
     */
    @JsonProperty
    private String text;

    /**
     * Reciever's phone number value
     *
     * @return phone number value
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Set message's reciever number
     *
     * @param receiver reciever phone number value
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }


    /**
     * Sender's phone number of the message
     *
     * @return sender's phone number value
     */
    public String getSender() {
        return sender;
    }

    /**
     * Set message's sender value
     *
     * @param sender message sender value
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Message's content
     *
     * @return content of the message
     */
    public String getText() {
        return text;
    }

    /**
     * Set message content
     *
     * @param text message content
     */
    public void setText(String text) {
        this.text = text;
    }
}