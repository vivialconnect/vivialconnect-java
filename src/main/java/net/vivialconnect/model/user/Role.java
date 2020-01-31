package net.vivialconnect.model.user;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An user has role that allow to operate over a Vivial Connect account.
 * <p>
 * A role can be helpful for manage multiple users in the same account, specifying actions that must
 * belong to every registered user.
 */
public class Role {

    /**
     * Unique identifier of the role object
     */
    @JsonProperty
    private int id;

    /**
     * Determine if this role is active or not
     */
    @JsonProperty
    private boolean active;

    /**
     * Creation date (UTC) of the role in ISO 8601 format
     */
    @JsonProperty("date_created")
    private Date dateCreated;

    /**
     * Last modification date (UTC) of the role in ISO 8601 format
     */
    @JsonProperty("date_modified")
    private Date dateModified;

    /**
     * Name of the role
     */
    @JsonProperty
    private String name;

    /**
     * Role's description
     */
    @JsonProperty
    private String description;

    /**
     * Role's type
     */
    @JsonProperty("role_type")
    private String roleType;

    /**
     * Unique identifier of the Role object
     *
     * @return role ID
     */
    public int getId() {
        return id;
    }

    /**
     * Set ID of the role object
     *
     * @param id Role ID value
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Determine if the role is active or note
     *
     * @return true if the role is active, false if not
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Change active value of the role
     *
     * @param active role's active value
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Creation date of the role
     *
     * @return creation date of the role
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * Set creation date of the role
     *
     * @param dateCreated creation date value
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Last modification date of the role
     *
     * @return last modification date of the role
     */
    public Date getDateModified() {
        return dateModified;
    }

    /**
     * Set last modification date of the role
     *
     * @param dateModified last modification date for the role
     */
    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    /**
     * Name of the role
     *
     * @return name of the role
     */
    public String getName() {
        return name;
    }

    /**
     * Set name of the role
     *
     * @param name name of the role
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Description of the role
     *
     * @return description value of the role
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description of the role
     *
     * @param description description of the role
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Type of the role
     *
     * @return type of the role value
     */
    public String getRoleType() {
        return roleType;
    }

    /**
     * Set role type
     *
     * @param roleType role type value
     */
    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
}