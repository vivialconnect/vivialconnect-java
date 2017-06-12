package net.vivialconnect.model.log;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LogData{

    @JsonProperty
    private String receiver;

    @JsonProperty
    private String sender;

    @JsonProperty
    private String text;


    public String getReceiver(){
        return receiver;
    }


    public void setReceiver(String receiver){
        this.receiver = receiver;
    }


    public String getSender(){
        return sender;
    }


    public void setSender(String sender){
        this.sender = sender;
    }


    public String getText(){
        return text;
    }


    public void setText(String text){
        this.text = text;
    }
}