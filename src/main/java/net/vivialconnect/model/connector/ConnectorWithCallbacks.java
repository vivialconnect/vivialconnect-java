package net.vivialconnect.model.connector;

import java.util.Date;
import java.util.List;

public interface ConnectorWithCallbacks {
	
    Date getDateModified();
	
	
    List<Callback> getCallbacks();
}