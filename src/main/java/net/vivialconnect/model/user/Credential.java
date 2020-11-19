package net.vivialconnect.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.Date;

/**
 * Represents user's API credential
 */
@JsonRootName("credential")
public class Credential {

    /**
     * Credential ID
     */
    @JsonProperty
    private int id;

    /**
     * Credential status, if true it is enabled, disabled otherwise
     */
    @JsonProperty
    private boolean active;

    /**
     * API Key value
     */
    @JsonProperty("api_key")
    private String apiKey;

    /**
     * API secret value. This field will be filled only when a new credential is created
     */
    @JsonProperty("api_secret")
    private String apiSecret;
    /**
     * Credential creation date
     */
    @JsonProperty("date_created")
    private Date dateCreated;

    /**
     * Credential modification date
     */
    @JsonProperty("date_modified")
    private Date dateModified;

    /**
     * Credential name
     */
    @JsonProperty
    private String name;

    /**
     * Owner of the credential
     */
    @JsonProperty
    private User user;

    public Credential() {
    }

    public Credential(int id, boolean active, String apiKey, String apiSecret, Date dateCreated, Date dateModified, String name, User user) {
        this.id = id;
        this.active = active;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.name = name;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }
}
