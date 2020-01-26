package net.vivialconnect.model.number;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Wrapper class used for map the result of a collection of tags returned by the API.
 */
public class TagCollection {

    /**
     * Collection of tags.
     */
    @JsonProperty("tags")
    private Map<String, String> tags;

    public TagCollection() {
    }

    /**
     * Convenient constructor for create and set a collection of tags.
     *
     * @param tags collection of tags value
     */
    public TagCollection(Map<String, String> tags) {
        this.tags = tags;
    }

    /**
     * Collection of tags
     *
     * @return collection of tags
     */
    public Map<String, String> getTags() {
        return tags;
    }

    /**
     * Set the tags collection value
     *
     * @param tags tags collection value
     */
    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return String.valueOf(tags);
    }
}
