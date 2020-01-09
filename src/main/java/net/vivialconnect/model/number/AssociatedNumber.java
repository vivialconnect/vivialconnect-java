package net.vivialconnect.model.number;

import java.util.Date;
import java.util.Map;

import net.vivialconnect.model.error.VivialConnectException;

public interface AssociatedNumber extends INumber{
	
    AssociatedNumber update() throws VivialConnectException;


    AssociatedNumber updateLocalNumber() throws VivialConnectException;


    boolean delete() throws VivialConnectException;


    boolean deleteLocalNumber() throws VivialConnectException;


    NumberInfo lookup() throws VivialConnectException;


    int getId();


    void setId(int id);


    Date getDateCreated();


    void setDateCreated(Date dateCreated);


    Date getDateModified();


    void setDateModified(Date dateModified);


    int getAccountId();


    void setAccountId(int accountId);


    String getVoiceForwardingNumber();


    void setVoiceForwardingNumber(String voiceForwardingNumber);


    /* Capabilities getCapabilities();


    void setCapabilities(Capabilities capabilities); */


    boolean isActive();


    void setActive(boolean active);


    String getStatusTextUrl();


    String getIncomingTextUrl();


    String getIncomingTextMethod();


    String getIncomingTextFallbackUrl();


    String getIncomingTextFallbackMethod();


    int getConnectorId();


    void setLata(String lata);


    void setCity(String city);


    void setRegion(String region);


    void setRateCenter(String rateCenter);

    TagCollection updateTags(Map<String, String> tags) throws VivialConnectException;

    TagCollection fetchTags() throws  VivialConnectException;

    TagCollection deleteTags(Map<String, String> tags) throws VivialConnectException;
}