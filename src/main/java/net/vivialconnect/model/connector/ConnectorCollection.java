package net.vivialconnect.model.connector;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Wrapper class for a collection of connectors
 */
public class ConnectorCollection {

    /**
     * Collection of connectors
     */
    @JsonProperty
    private List<Connector> connectors;


    /**
     * Returns a collection of connectors
     *
     * @return list of connectors
     */
    public List<Connector> getConnectors() {
        return connectors;
    }

    /**
     * Set a collection of connectors
     *
     * @param connectors connector list
     */
    public void setConnectors(List<Connector> connectors) {
        this.connectors = connectors;
    }
}