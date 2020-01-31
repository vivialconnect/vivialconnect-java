package net.vivialconnect.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class for mapping count responses from the API
 */
public class ResourceCount {

    /**
     * Count value for a Resource
     */
    @JsonProperty
    private int count;

    /**
     * Count value for a Resource
     *
     * @return count value
     */
    public int getCount() {
        return count;
    }

    /**
     * Set count value
     *
     * @param count count value
     */
    public void setCount(int count) {
        this.count = count;
    }
}
