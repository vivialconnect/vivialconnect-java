package net.vivialconnect.model.connector;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import net.vivialconnect.model.ResourceCount;
import net.vivialconnect.model.VivialConnectResource;
import net.vivialconnect.model.error.VivialConnectException;

@JsonRootName("phone_number")
public class PhoneNumber extends VivialConnectResource{

    private static final long serialVersionUID = -621133784366767246L;

    /** A string containing the phone number */
    @JsonProperty("phone_number")
    private String phoneNumber;

    /** An integer representing the id of the PhoneNumber resource associated */
    @JsonProperty("phone_number_id")
    private int phoneNumberId;

    public PhoneNumber(){

    }

    public PhoneNumber(int phoneNumberId, String phoneNumber){
        this.phoneNumberId = phoneNumberId;
        this.phoneNumber = phoneNumber;
    }


    public static ConnectorWithPhoneNumbers getPhoneNumbers(int connectorId) throws VivialConnectException{
        return request(RequestMethod.GET, classURLWithSuffix(Connector.class, String.format("%d/phone_numbers", connectorId)), null, null, Connector.class);
    }


    public static int count(int connectorId) throws VivialConnectException{
        return request(RequestMethod.GET, classURLWithSuffix(Connector.class, String.format("%d/phone_numbers/count", connectorId)), null, null, ResourceCount.class).getCount();
    }


    public String getPhoneNumber(){
        return phoneNumber;
    }


    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }


    public int getPhoneNumberId(){
        return phoneNumberId;
    }


    public void setPhoneNumberId(int phoneNumberId){
        this.phoneNumberId = phoneNumberId;
    }
}