package net.vivialconnect.model.account;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a service associated to an account
 */
public class Service {

    /**
     * Unique identifier of the service object
     */
    @JsonProperty
    private int id;

    /**
     * Creation date (UTC) of the service in ISO 8601 format
     */
    @JsonProperty("date_created")
    private Date dateCreated;

    /**
     * Last modification date (UTC) of service in ISO 8601 format
     */
    @JsonProperty("date_modified")
    private Date dateModified;

    /**
     * The name of the service
     */
    @JsonProperty
    private String name;

    @JsonProperty("service_type")
    private String serviceType;

    @JsonProperty
    private boolean active;

    /**
     * Description of the service
     */
    @JsonProperty
    private String description;

    /**
     * Unique identifier of the service object
     *
     * @return ID value
     */
    public int getId() {
        return id;
    }

    /**
     * Set the ID for the service object
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Creation date of the service
     *
     * @return creation date value
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * Set the creation date of the service
     *
     * @param dateCreated creation date value
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Last modification date of the service object
     *
     * @return last modification value
     */
    public Date getDateModified() {
        return dateModified;
    }

    /**
     * Set the last modification date of the service
     *
     * @param dateModified last modification value
     */
    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    /**
     * Service name
     *
     * @return service name value
     */
    public String getName() {
        return name;
    }

    /**
     * Set service name
     *
     * @param name service name value
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Service type
     *
     * @return service type value
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * Set service type
     *
     * @param serviceType service type value
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * Determine if the service is active or not
     *
     * @return service's active value
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Set service's active value
     *
     * @param active service active value
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Service description
     *
     * @return service description value
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set service description
     *
     * @param description service description value
     */
    public void setDescription(String description) {
        this.description = description;
    }
}