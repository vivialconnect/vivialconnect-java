package net.vivialconnect.model.message;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageCollection{
	
    @JsonProperty
    private List<Message> messages;


    public List<Message> getMessages(){
        return messages;
    }


    public void setMessages(List<Message> messages){
        this.messages = messages;
    }
}