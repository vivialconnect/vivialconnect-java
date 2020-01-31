package net.vivialconnect.model.message;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Wrapper class for map a list of messages returned by the API.
 */
public class MessageCollection{

    /**
     * List of messages
     */
    @JsonProperty
    private List<Message> messages;

    /**
     * @return Get a list of messages
     */
    public List<Message> getMessages(){
        return messages;
    }

    /**
     * @param messages list of messages.
     */
    public void setMessages(List<Message> messages){
        this.messages = messages;
    }
}