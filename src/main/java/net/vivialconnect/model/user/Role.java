package net.vivialconnect.model.user;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Role{
	
    /** Unique identifier of the role object */
    @JsonProperty
    private int id;

    @JsonProperty
    private boolean active;

    /** Creation date (UTC) of the role in ISO 8601 format */
    @JsonProperty("date_created")
    private Date dateCreated;

    /** Last modification date (UTC) of the role in ISO 8601 format */
    @JsonProperty("date_modified")
    private Date dateModified;

    /** Name of the role  */
    @JsonProperty
    private String name;

    /** Role's description  */
    @JsonProperty
    private String description;

    /** Role's type  */
    @JsonProperty("role_type")
    private String roleType;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public boolean isActive(){
        return active;
    }

    public void setActive(boolean active){
        this.active = active;
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

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getRoleType(){
        return roleType;
    }

    public void setRoleType(String roleType){
        this.roleType = roleType;
    }
}