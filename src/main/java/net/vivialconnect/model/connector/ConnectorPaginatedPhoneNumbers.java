package net.vivialconnect.model.connector;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Wrapper class for a connector with list of phone numbers
 */
public class ConnectorPaginatedPhoneNumbers {

    @JsonProperty("connector")
    private Connector connector;

    /**
     * Count of numbers in the connector
     */
    @JsonProperty("count")
    private int count;

    /**
     * Next page value
     */
    @JsonProperty("next")
    private int next;

    /**
     * Count of pages
     */
    @JsonProperty("pages")
    private int pages;

    /**
     * Previous page value
     */
    @JsonProperty("previous")
    private int previous;

    /**
     * Connector with its phone numbers
     * @return Connector instance
     */
    public Connector getConnector() {
        return setupConnector(connector);
    }

    private Connector setupConnector(Connector connector){

        if(connector != null){

            connector.setPages(pages);
            connector.setPreviousPage(previous);
            connector.setNextPage(next);
            connector.setPhoneNumbersCount(count);

        }

        return  connector;
    }

}
