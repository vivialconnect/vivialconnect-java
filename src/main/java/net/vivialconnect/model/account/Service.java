package net.vivialconnect.model.account;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Service{
	
    /** Unique identifier of the service object */
    @JsonProperty
    private int id;

    /** Creation date (UTC) of the service in ISO 8601 format */
    @JsonProperty("date_created")
    private Date dateCreated;

    /** Last modification date (UTC) of service in ISO 8601 format */
    @JsonProperty("date_modified")
    private Date dateModified;

    /** The name of the service */
    @JsonProperty
    private String name;

    @JsonProperty("service_type")
    private String serviceType;

    @JsonProperty
    private boolean active;

    @JsonProperty
    private String description;


    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public Date getDateCreated(){
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated){
        this.dateCreated = dateCreated;
    }

    public Date getDateModified(){
        return dateModified;
    }

    public void setDateModified(Date dateModified){
        this.dateModified = dateModified;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getServiceType(){
        return serviceType;
    }

    public void setServiceType(String serviceType){
        this.serviceType = serviceType;
    }

    public boolean isActive(){
        return active;
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }
}