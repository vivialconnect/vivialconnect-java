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

@JsonRootName("user")
public class User extends VivialConnectResource{

    private static final long serialVersionUID = 6871296061820754520L;

    /** Unique identifier of the user object */
    @JsonProperty
    private int id;

    /** Creation date (UTC) of the user in ISO 8601 format */
    @JsonProperty("date_created")
    private Date dateCreated;

    /** Last modification date (UTC) of user in ISO 8601 format */
    @JsonProperty("date_modified")
    private Date dateModified;

    /**
     * Unique identifier of the account or subaccount associated with the user
     */
    @JsonProperty("account_id")
    private int accountId;

    @JsonProperty
    private boolean active;

    @JsonProperty
    private boolean verified;

    @JsonProperty("api_key")
    private String apiKey;

    @JsonProperty
    private String timezone;

    @JsonProperty
    private String username;

    /** User's first name */
    @JsonProperty("first_name")
    private String firstName;

    /** User's last name */
    @JsonProperty("last_name")
    private String lastName;

    /** User's email address */
    @JsonProperty
    private String email;

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
     * 
     * @return the User that was found given the id
     * @throws VivialConnectException if there is an API-level error
     * 
     * @see #getUsers()
     * @see #getUsers(Map)
     */
    public static User getUserById(int userId) throws VivialConnectException{
        return request(RequestMethod.GET, classURLWithSuffix(User.class, String.valueOf(userId)), null, null, User.class);
    }

    /**
     * Gets all users for this Account using the API.
     * <p>
     * If no User were found for this {@link Account}, the method will return an empty {@link List}
     * 
     * @return a list of users
     * @throws VivialConnectException if there is an API-level error
     * 
     * @see #getUserById(int)
     * @see #getUsers(Map) 
     */
    public static List<User> getUsers() throws VivialConnectException{
        return getUsers(null);
    }

    /**
     * Search and filter every user for this Account using the API.
     * <p>
     * If no {@link User} were found for this {@link Account}, the method will return an empty {@link List}.
     * 
     * @param queryParams a map of {@link String } key-value pairs used to filter results, possible values are:
     * <p>
     * <code>page</code> – Page number within the returned list of associated phone numbers. Default value: 1.
     * <p>
     * <code>limit</code> – Number of results to return per page. Default value: 50. Maximum value: 150.
     * 
     * @return a list of users
     * @throws VivialConnectException if there is an API-level error
     * 
     * @see #getUsers()
     * @see #getUserById(int) 
     */
    public static List<User> getUsers(Map<String, String> queryParams) throws VivialConnectException{
        return request(RequestMethod.GET, classURL(User.class), null, queryParams, UserCollection.class).getUsers();
    }

    /**
     * Total number of users in the account specified. If there are none, this method will return <code>0</code>.
     * 
     * @return user count
     * @throws VivialConnectException if there is an API-level error
     *
     */
    public static int count() throws VivialConnectException{
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
     *
     */
    public boolean delete() throws VivialConnectException{
        try{
            request(RequestMethod.DELETE, classURLWithSuffix(User.class, String.valueOf(getId())), null, null, String.class);
        }catch (NoContentException nce){
            return true;
        }

        return false;
    }

    /**
     * Updates the user's password.
     * <p>
     * Returns <code>true</code> if the password was successfully updated.
     * 
     * @param oldPassword {@link String } representing actual password for this user
     * @param newPassword {@link String } representing new password to be updated
     * 
     * @return whether the password was updated or not
     * @throws VivialConnectException if there is an API-level error
     *
     */
    public boolean changePassword(String oldPassword, String newPassword) throws VivialConnectException{
        JsonBodyBuilder builder = JsonBodyBuilder.forClass(User.class)
                                                 .addParamPair("_password", oldPassword)
                                                 .addParamPair("password", newPassword);

        String result = request(RequestMethod.PUT, classURLWithSuffix(User.class, String.format("%d/profile/password", getId())),
                                                                                            builder.build(), null, String.class);

        return "{}".equals(result);
    }


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

    public int getAccountId(){
        return accountId;
    }

    public void setAccountId(int accountId){
        this.accountId = accountId;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public boolean isActive(){
        return active;
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public boolean isVerified(){
        return verified;
    }

    public void setVerified(boolean verified){
        this.verified = verified;
    }

    public String getApiKey(){
        return apiKey;
    }

    public void setApiKey(String apiKey){
        this.apiKey = apiKey;
    }

    public String getTimezone(){
        return timezone;
    }

    public void setTimezone(String timezone){
        this.timezone = timezone;
    }

    public List<Role> getRoles(){
        return roles;
    }

    public void setRoles(List<Role> roles){
        this.roles = roles;
    }
}