package net.vivialconnect.model.connector;

import java.util.Date;
import java.util.List;

/**
 * This interface defines the properties and methods for a connector that contains callbacks
 */
public interface ConnectorWithCallbacks {

    /**
     * Must return the last modification date of the connector
     *
     * @return connector's last modification date
     */
    Date getDateModified();

    /**
     * Must return a list of callbacks
     *
     * @return connector's callbacks list
     */
    List<Callback> getCallbacks();
}