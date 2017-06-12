package net.vivialconnect.model.connector;

import java.util.Date;
import java.util.List;

public interface ConnectorWithPhoneNumbers{

    Date getDateModified();
	
	
    List<PhoneNumber> getPhoneNumbers();
}