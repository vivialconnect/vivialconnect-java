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

    /**
     * Information about errors in device lookup
     * @return information value
     */
    public String getError(){
        return error;
    }

    /**
     * Set error value
     * @param error error value
     */
    public void setError(String error){
        this.error = error;
    }

    /**
     * Model name of the device
     * @return device's model value
     */
    public String getModel(){
        return model;
    }

    /**
     * Device's model name
     * @param model device's model value
     */
    public void setModel(String model){
        this.model = model;
    }
}