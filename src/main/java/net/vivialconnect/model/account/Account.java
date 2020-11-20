package net.vivialconnect.model.account;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import net.vivialconnect.model.VivialConnectResource;
import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.format.JsonBodyBuilder;
import net.vivialconnect.model.error.BadRequestException;
import net.vivialconnect.model.error.ServerErrorException;
import net.vivialconnect.model.error.ApiRequestException;
import net.vivialconnect.model.error.ForbiddenAccessException;
import net.vivialconnect.model.error.UnauthorizedAccessException;


/**
 * To access the Vivial Connect API, you must have an account. To set up an account, visit <a href="https://www.vivialconnect.net/">Vivial Connect.</a>
 *
 * Every Vivial Connect account has at least one user. You can manage users and accounts using the USERS and ACCOUNTS endpoints in the API.
 *
 * For more info visit: <a href="https://dashboard.vivialconnect.net/docs/api/accounts.html">Accounts documentation</a>.
 */
@JsonRootName("account")
public class Account extends VivialConnectResource {

    private static final long serialVersionUID = -2624039897600671223L;

    /**
     * Unique identifier of the account object
     */
    @JsonProperty
    private int id;

    /**
     * Creation date (UTC) of the account in ISO 8601 format
     */
    @JsonProperty("date_created")
    private Date dateCreated;

    /**
     * Last modification date (UTC) of account in ISO 8601 format
     */
    @JsonProperty("date_modified")
    private Date dateModified;

    /**
     * Unique identifier of the parent account
     */
    @JsonProperty("account_id")
    private int accountId;

    /**
     * Account name as it is displayed to users (for example, the name of your company)
     */
    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty
    private boolean active;

    /**
     * Account's contacts
     */
    @JsonProperty
    private List<Contact> contacts;

    /**
     * Account's services
     */
    @JsonProperty
    private List<Service> services;


    /**
     * Retrieve the information for this Account.
     *
     * @return account object
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params and/or payload  are not valid
     * @throws UnauthorizedAccessException if any of the auth properties account ID, API Key and/or API secret are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    public static Account getAccount() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        return request(RequestMethod.GET, singleClassURL(Account.class), null, null, Account.class);
    }


    /**
     * Updates this account by sending to the server the properties that were changed via
     * this class's set methods.
     * <p>
     * For more details on which properties can be updated, refer to the VivialConnect API documentation's
     * <a href="https://dashboard.vivialconnect.net/docs/api/accounts.html#put--api-v1.0-accounts-(int-id).json">account section.</a>
     * <p>
     * If the account you're trying to update does not exist, a {@link VivialConnectException}
     * holding a 404 response code will be thrown.
     *
     * @return this instance of {@link Account} with the updated properties
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params and/or payload  are not valid
     * @throws UnauthorizedAccessException if any of the auth properties account ID, API Key and/or API secret are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    public Account update() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        Account updatedAccount = request(RequestMethod.PUT, singleClassURL(Account.class),
                buildJsonBodyForUpdate(), null, Account.class);
        updateFields(updatedAccount);

        return this;
    }


    private String buildJsonBodyForUpdate() {
        JsonBodyBuilder builder = JsonBodyBuilder.forClass(Account.class);
        fillOptionalFieldsForUpdate(builder);

        return builder.build();
    }


    private void fillOptionalFieldsForUpdate(JsonBodyBuilder builder) {
        ifParamValidAddToBuilder(builder, "id", getId());
        ifParamValidAddToBuilder(builder, "company_name", getCompanyName());
    }


    private void updateFields(Account updatedAccount) {
        this.id = updatedAccount.getId();
        this.accountId = updatedAccount.getAccountId();
        this.active = updatedAccount.isActive();
        this.companyName = updatedAccount.getCompanyName();
        this.dateCreated = updatedAccount.getDateCreated();
        this.dateModified = updatedAccount.getDateModified();
        this.contacts = updatedAccount.getContacts();
        this.services = updatedAccount.getServices();
    }

    /**
     * Returns a list of transactions between a time interval
     * @param startTime Start date and time in ISO 8601 format like YYYYMMDDThhmmssZ.
     * @param endTime End date and time in ISO 8601 format like YYYYMMDDThhmmssZ
     * @return list of transactions
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params and/or payload  are not valid
     * @throws UnauthorizedAccessException if any of the auth properties account ID, API Key and/or API secret are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    public List<Transaction> getTransactions(String startTime, String endTime) throws BadRequestException, ForbiddenAccessException, ServerErrorException, ApiRequestException, UnauthorizedAccessException {
        return getTransactions(startTime, endTime, null, 0, 0);
    }

    /**
     * Returns a list of transactions between a time interval, using paging and quanity limit.
     * @param startTime Start date and time in ISO 8601 format like YYYYMMDDThhmmssZ.
     * @param endTime End date and time in ISO 8601 format like YYYYMMDDThhmmssZ
     * @param page page number to retrieve
     * @param limit quantity of transactions to return
     * @return list of transactions
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params and/or payload  are not valid
     * @throws UnauthorizedAccessException if any of the auth properties account ID, API Key and/or API secret are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    public List<Transaction> getTransactions(String startTime, String endTime, int page, int limit) throws BadRequestException, ForbiddenAccessException, ServerErrorException, ApiRequestException, UnauthorizedAccessException {
        return getTransactions(startTime, endTime, null, page, limit);
    }

    /**
     * Returns a list of transactions between a time interval filtering by transaction_type
     * @param startTime Start date and time in ISO 8601 format like YYYYMMDDThhmmssZ.
     * @param endTime End date and time in ISO 8601 format like YYYYMMDDThhmmssZ
     * @param transactionType page number to retrieve
     * @return list of transactions
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params and/or payload  are not valid
     * @throws UnauthorizedAccessException if any of the auth properties account ID, API Key and/or API secret are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    public List<Transaction> getTransactions(String startTime, String endTime,TransactionType transactionType) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        return getTransactions(startTime, endTime, transactionType, 0, 0);
    }

    /**
     * Returns a list of transactions between a time interval filtering by transaction_type, using pagination and limit
     * @param startTime Start date and time in ISO 8601 format like YYYYMMDDThhmmssZ.
     * @param endTime End date and time in ISO 8601 format like YYYYMMDDThhmmssZ
     * @param transactionType page number to retrieve
     * @param page page number to retrieve
     * @param limit quantity of transactions to return
     * @return list of transactions
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params and/or payload  are not valid
     * @throws UnauthorizedAccessException if any of the auth properties account ID, API Key and/or API secret are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    public List<Transaction> getTransactions(String startTime, String endTime,TransactionType transactionType, int page, int limit) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {

        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("start_time", startTime);
        queryParams.put("end_time", endTime);

        if(page > 0)
           queryParams.put("page", String.valueOf(page));

        if(limit > 0)
            queryParams.put("limit", String.valueOf(limit));

        if(transactionType != null)
            queryParams.put("include_types[]", transactionType.name().toLowerCase());

        TransactionResponse response = request(RequestMethod.GET, classURLWithoutSuffix(Transaction.class), null, queryParams, TransactionResponse.class);
        return response.getTransactions();
    }

    /**
     * Unique identifier of the account object
     *
     * @return account ID value
     */
    public int getId() {
        return id;
    }

    /**
     * Set the account object ID
     *
     * @param id Account object ID value
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Creation date of the account
     *
     * @return creation date of the account
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * Set the creation date of the account
     *
     * @param dateCreated create date of the account
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Last modification date of account
     *
     * @return modification date of the account
     */
    public Date getDateModified() {
        return dateModified;
    }

    /**
     * Set the account modification date
     *
     * @param dateModified modification date of the account
     */
    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    /**
     * Account ID of the user
     *
     * @return account ID value
     */
    public int getAccountId() {
        return accountId;
    }

    /**
     * Set the account ID of the user
     *
     * @param accountId account ID of the user
     */
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    /**
     * Company name of the account
     *
     * @return company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Set company name of the account
     *
     * @param companyName company name value
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Account active value
     *
     * @return true if the account is active, false if not
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Set activation value of the account
     *
     * @param active active value
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * List of account contacts
     *
     * @return contacts of the account
     */
    public List<Contact> getContacts() {
        return contacts;
    }

    /**
     * Set contacts list for the account
     *
     * @param contacts list of contacts
     */
    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    /**
     * Services list for the account
     *
     * @return list of services of the account
     */
    public List<Service> getServices() {
        return services;
    }

    /**
     * Set list of services for the account
     *
     * @param services list of services.
     */
    public void setServices(List<Service> services) {
        this.services = services;
    }
}