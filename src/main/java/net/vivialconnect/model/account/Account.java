package net.vivialconnect.model.account;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import net.vivialconnect.model.VivialConnectResource;
import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.format.JsonBodyBuilder;
import net.vivialconnect.model.log.LogCollection;

@JsonRootName("account")
public class Account extends VivialConnectResource{

    private static final long serialVersionUID = -2624039897600671223L;

    /** Unique identifier of the account object */
    @JsonProperty
    private int id;

    /** Creation date (UTC) of the account in ISO 8601 format */
    @JsonProperty("date_created")
    private Date dateCreated;

    /** Last modification date (UTC) of account in ISO 8601 format */
    @JsonProperty("date_modified")
    private Date dateModified;

    /**
     * Unique identifier of the parent account
     */
    @JsonProperty("account_id")
    private int accountId;

    /** Account name as it is displayed to users (for example, the name of your company) */
    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty
    private boolean active;

    /** Account's contacts */
    @JsonProperty
    private List<Contact> contacts;

    /** Account's services */
    @JsonProperty
    private List<Service> services;

    static {
        classesWithoutRootValue.add(LogCollection.class);
        classesWithoutRootValue.add(ContactCollection.class);
    }

    /**
     * Retrieve the information for this Account.
     * 
     * @return account object
     * @throws VivialConnectException if there is an API-level error
     * 
     */
    public static Account getAccount() throws VivialConnectException{
        return request(RequestMethod.GET, singleClassURL(Account.class), null, null, Account.class);
    }

    /**
     * Updates information about the account.
     * 
     * @return account object
     * @throws VivialConnectException if there is an API-level error
     *
     */
    
    /**
     * Updates this account by sending to the server the properties that were changed via
     * this class's set methods.
     * <p>
     * For more details on which properties can be updated, refer to the VivialConnect API documentation's
     * <a href="https://www.vivialconnect.net/docs/api.html#put--api-v1.0-accounts-(int-id).json">account section.</a>
     * <p>
     * If the account you're trying to update does not exist, a {@link VivialConnectException}
     * holding a 404 response code will be thrown.
     * 
     * @return this instance of {@link Account} with the updated properties
     * @throws VivialConnectException if there is an API-level error
     */
    public Account update() throws VivialConnectException{
        Account updatedAccount = request(RequestMethod.PUT, singleClassURL(Account.class),
                                        buildJsonBodyForUpdate(), null, Account.class);
        updateFields(updatedAccount);
        
        return this;
    }


    private String buildJsonBodyForUpdate(){
        JsonBodyBuilder builder = JsonBodyBuilder.forClass(Account.class);
        fillOptionalFieldsForUpdate(builder);

        return builder.build();
    }


    private void fillOptionalFieldsForUpdate(JsonBodyBuilder builder){
        ifParamValidAddToBuilder(builder, "id", getId());
        ifParamValidAddToBuilder(builder, "company_name", getCompanyName());
    }


    private void updateFields(Account updatedAccount){
        this.id = updatedAccount.getId();
        this.accountId = updatedAccount.getAccountId();
        this.active = updatedAccount.isActive();
        this.companyName = updatedAccount.getCompanyName();
        this.dateCreated = updatedAccount.getDateCreated();
        this.dateModified = updatedAccount.getDateModified();
        this.contacts = updatedAccount.getContacts();
        this.services = updatedAccount.getServices();
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


    public String getCompanyName(){
        return companyName;
    }


    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }


    public boolean isActive(){
        return active;
    }


    public void setActive(boolean active){
        this.active = active;
    }


    public List<Contact> getContacts(){
        return contacts;
    }


    public void setContacts(List<Contact> contacts){
        this.contacts = contacts;
    }


    public List<Service> getServices(){
        return services;
    }


    public void setServices(List<Service> services){
        this.services = services;
    }
}