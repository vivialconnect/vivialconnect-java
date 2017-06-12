package net.vivialconnect.model.connector;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConnectorCollection{

    @JsonProperty
    private List<Connector> connectors;


    public List<Connector> getConnectors(){
        return connectors;
    }


    public void setConnectors(List<Connector> connectors){
        this.connectors = connectors;
    }
}