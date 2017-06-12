package net.vivialconnect.model.number;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Details about the device type used by the phone number. Results include:
 * 
 * <p>model - Model name of the device
 * <p>error - Information about errors in device lookup. For example, number is not mobile or carrier doesn’t support device lookup
 * 
 * */
public class Device{
	
    /** Information about errors in device lookup. For example, number is not mobile or carrier doesn’t support device lookup */
    @JsonProperty
    private String error;


    /** Model name of the device */
    @JsonProperty
    private String model;


    public String getError(){
        return error;
    }


    public void setError(String error){
        this.error = error;
    }


    public String getModel(){
        return model;
    }


    public void setModel(String model){
        this.model = model;
    }
}