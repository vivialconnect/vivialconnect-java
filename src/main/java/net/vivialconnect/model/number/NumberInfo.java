package net.vivialconnect.model.number;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import net.vivialconnect.model.VivialConnectResource;

/**
 * A class to hold information about the device type and carrier that is associated with a specific phone number.
 * <p>
 * This information can be useful for text messaging campaigns that target specific user experiences based on the
 * user’s phone number information.
 * */
@JsonRootName(value = "number_info")
public class NumberInfo extends VivialConnectResource{
	
    private static final long serialVersionUID = 8828026309873208187L;

    /** Details about the phone carrier providing service to the phone number */
    @JsonProperty
    private Carrier carrier;

    /** Details about the device type used by the phone number */
    @JsonProperty
    private Device device;

    /** Phone number for which you want device and carrier information */
    @JsonProperty("phone_number")
    private String phoneNumber;

    /** Creation date (UTC) of the number info in ISO 8601 format */
    @JsonProperty("date_created")
    private Date dateCreated;

    /** Last modification date (UTC) of number info in ISO 8601 format */
    @JsonProperty("date_modified")
    private Date dateModified;


    public Carrier getCarrier(){
        return carrier;
    }


    public void setCarrier(Carrier carrier){
        this.carrier = carrier;
    }


    public Device getDevice(){
        return device;
    }


    public void setDevice(Device device){
        this.device = device;
    }


    public String getPhoneNumber(){
        return phoneNumber;
    }


    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
}