package net.vivialconnect.model.number;

public interface INumber{
	
    String getName();


    void setName(String name);


    void setStatusTextUrl(String statusTextUrl);


    void setIncomingTextUrl(String incomingTextUrl);


    void setIncomingTextMethod(String incomingTextMethod);


    void setIncomingTextFallbackUrl(String incomingTextFallbackUrl);


    void setIncomingTextFallbackMethod(String incomingTextFallbackMethod);


    void setConnectorId(int connectorId);


    String getPhoneNumber();


    void setPhoneNumber(String phoneNumber);


    String getPhoneNumberType();


    void setPhoneNumberType(String phoneNumberType);


    String getCity();


    String getRegion();


    String getLata();


    String getRateCenter();
}