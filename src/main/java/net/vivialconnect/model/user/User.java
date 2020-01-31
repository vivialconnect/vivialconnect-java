package net.vivialconnect.model.user;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import net.vivialconnect.model.ResourceCount;
import net.vivialconnect.model.VivialConnectResource;
import net.vivialconnect.model.account.Account;
import net.vivialconnect.model.error.NoContentException;
import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.format.EmptyJson;
import net.vivialconnect.model.format.JsonBodyBuilder;

/**
 * class for get query and manage info about users of an account.
 * <p>
 * For more info visit <a href="https://dashboard.vivialconnect.net/docs/api/users.html#">Users</a>
 */
@JsonRootName("user")
public class User extends VivialConnectResource {

    private static final long serialVersionUID = 6871296061820754520L;

    /**
     * Unique identifier of the user object
     */
    @JsonProperty
    private int id;

    /**
     * Creation date (UTC) of the user in ISO 8601 format
     */
    @JsonProperty("date_created")
    private Date dateCreated;

    /**
     * Last modification date (UTC) of user in ISO 8601 format
     */
    @JsonProperty("date_modified")
    private Date dateModified;

    /**
     * Unique identifier of the account or subaccount associated with the user
     */
    @JsonProperty("account_id")
    private int accountId;

    /**
     * User's active status
     */
    @JsonProperty
    private boolean active;

    /**
     * Determine if the account was verified by the user after signed in
     */
    @JsonProperty
    private boolean verified;

    @JsonProperty("api_key")
    private String apiKey;

    /**
     * User's location time zone
     */
    @JsonProperty
    private String timezone;

    /**
     * log in user name
     */
    @JsonProperty
    private String username;

    /**
     * User's first name
     */
    @JsonProperty("first_name")
    private String firstName;

    /**
     * User's last name
     */
    @JsonProperty("last_name")
    private String lastName;

    /**
     * User's email address
     */
    @JsonProperty
    private String email;

    /**
     * List of roles associated to this user
     */
    @JsonProperty
    private List<Role> roles;

    static {
        classesWithoutRootValue.add(EmptyJson.class);
        classesWithoutRootValue.add(UserCollection.class);
    }

    /**
     * Search for a {@link User} by its ID using the API.
     * <p>
     * If the {@link User} is not found, a VivialConnectException will be thrown.
     *
     * @param userId the id of the user to look up
     * @return the User that was found given the id
     * @throws VivialConnectException if there is an API-level error
     * @see #getUsers()
     * @see #getUsers(Map)
     */
    public static User getUserById(int userId) throws VivialConnectException {
        return request(RequestMethod.GET, classURLWithSuffix(User.class, String.valueOf(userId)), null, null, User.class);
    }

    /**
     * Gets all users for this Account using the API.
     * <p>
     * If no User were found for this {@link Account}, the method will return an empty {@link List}
     *
     * @return a list of users
     * @throws VivialConnectException if there is an API-level error
     * @see #getUserById(int)
     * @see #getUsers(Map)
     */
    public static List<User> getUsers() throws VivialConnectException {
        return getUsers(null);
    }

    /**
     * Search and filter every user for this Account using the API.
     * <p>
     * If no {@link User} were found for this {@link Account}, the method will return an empty {@link List}.
     *
     * @param queryParams a map of {@link String } key-value pairs used to filter results, possible values are:
     *                    <p>
     *                    <code>page</code> – Page number within the returned list of users. Default value: 1.
     *                    <p>
     *                    <code>limit</code> – Number of results to return per page. Default value: 50. Maximum value: 150.
     *                    <code>order</code> – Sort field and direction formatted like: <code>[field]+[direction]</code> where direction is one of ‘asc’ or ‘desc’.
     *                    Default value: ‘id+asc’.
     * @return a list of users
     * @throws VivialConnectException if there is an API-level error
     * @see #getUsers()
     * @see #getUserById(int)
     */
    public static List<User> getUsers(Map<String, String> queryParams) throws VivialConnectException {
        return request(RequestMethod.GET, classURL(User.class), null, queryParams, UserCollection.class).getUsers();
    }

    /**
     * Total number of users in the account specified. If there are none, this method will return <code>0</code>.
     *
     * @return user count
     * @throws VivialConnectException if there is an API-level error
     */
    public static int count() throws VivialConnectException {
        return request(RequestMethod.GET, classURLWithSuffix(User.class, "count"), null, null, ResourceCount.class).getCount();
    }

    /**
     * Deletes this user from the database, dissociating it from the account.
     * <p>
     * If the user you're trying to delete does not exist, a {@link VivialConnectException}
     * holding a 404 response code will be thrown.
     *
     * @return a boolean value, indicating whether the user was deleted or not
     * @throws VivialConnectException if there is an API-level error
     */
    public boolean delete() throws VivialConnectException {
        try {
            request(RequestMethod.DELETE, classURLWithSuffix(User.class, String.valueOf(getId())), null, null, String.class);
        } catch (NoContentException nce) {
            return true;
        }

        return false;
    }

    /**
     * Unique identifier of the user object
     *
     * @return User's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Set user's unique identifier
     *
     * @param id User ID value
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Creation date of the user
     *
     * @return creation date of the user
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * Set creation date of the user
     *
     * @param dateCreated creation date of the user
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Last modification date of the user
     *
     * @return last modification date of the user
     */
    public Date getDateModified() {
        return dateModified;
    }

    /**
     * Set modified date of the user
     *
     * @param dateModified modified date of the user
     */
    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    /**
     * Unique identifier of the account that this user is part of.
     *
     * @return account ID value
     */
    public int getAccountId() {
        return accountId;
    }

    /**
     * Set account ID
     *
     * @param accountId account ID value
     */
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    /**
     * Log in username
     *
     * @return login user name
     */
    public String getUsername() {
        return username;
    }

    /**
     * Log in user name
     *
     * @param username user name value
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * User’s first name.
     *
     * @return User’s first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * User’s first name. Max. length: 128 characters
     *
     * @param firstName first name of the user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * User’s last name.
     *
     * @return User’s last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * User’s last name. Max. length: 128 characters
     *
     * @param lastName User's last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * User’s email address
     *
     * @return user's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * User’s email address. Max. length: 128 characters
     *
     * @param email email value
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Indicate if the user is active or not
     *
     * @return true if the user is active, false otherwise
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Set user's active status
     *
     * @param active active value
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Indicate if the account was verified by the user after registration.
     *
     * @return true if the user was verified, false otherwise
     */
    public boolean isVerified() {
        return verified;
    }

    /**
     * Set verification status
     *
     * @param verified verification status value
     */
    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * User's time zone
     *
     * @return User's timezone
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * Set user's timezone
     *
     * @param timezone user's timezone value
     */
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    /**
     * List of roles of the user
     *
     * @return list of roles
     */
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * Set list of roles of the user
     *
     * @param roles list of roles
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
