package net.vivialconnect.model.error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorMessage{

    @JsonProperty("message")
    private String errorMessage;


    public String getErrorMessage(){
        return errorMessage;
    }


    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }
}