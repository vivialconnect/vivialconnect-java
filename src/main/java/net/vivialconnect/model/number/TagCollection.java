package net.vivialconnect.model.number;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class TagCollection {

    @JsonProperty("tags")
    private Map<String, String> tags;

    public TagCollection() {
    }

    public TagCollection(Map<String, String> tags) {
        this.tags = tags;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return String.valueOf(tags);
    }
}
