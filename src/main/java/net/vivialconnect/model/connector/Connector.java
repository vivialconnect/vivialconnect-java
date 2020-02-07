package net.vivialconnect.model.connector;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import net.vivialconnect.model.ResourceCount;
import net.vivialconnect.model.VivialConnectResource;
import net.vivialconnect.model.error.NoContentException;
import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.format.JsonBodyBuilder;
import net.vivialconnect.model.error.BadRequestException;
import net.vivialconnect.model.error.ServerErrorException;
import net.vivialconnect.model.error.ApiRequestException;
import net.vivialconnect.model.error.ForbiddenAccessException;
import net.vivialconnect.model.error.UnauthorizedAccessException;

/**
 * Connector is used for managing connectors and their components.
 * Connectors act as configurable gateways through which messages can be sent, providing services like callback configuration and FROM number pooling.
 * <p>
 * Connectors enable to:
 * <ul>
 *     <li>Send messages from a pool of numbers</li>
 *     <li>Change FROM numbers without editing your code</li>
 *     <li>Manage callbacks for multiple numbers</li>
 * </ul>
 */
@JsonRootName("connector")
public class Connector extends VivialConnectResource implements ConnectorWithCallbacks, ConnectorWithPhoneNumbers {

    private static final long serialVersionUID = 9106799578930523035L;

    /**
     * Unique identifier of the Connector object
     */
    @JsonProperty
    private int id;

    /**
     * Creation date (UTC) of the Connector in ISO 8601 format
     */
    @JsonProperty("date_created")
    private Date dateCreated;

    /**
     * Last modification date (UTC) of the Connector in ISO 8601 format
     */
    @JsonProperty("date_modified")
    private Date dateModified;

    /**
     * Unique identifier of the owning Account
     */
    @JsonProperty("account_id")
    private int accountId;

    @JsonProperty
    private boolean active;

    /**
     * User-defined descriptive label
     */
    @JsonProperty
    private String name;

    /**
     * A list of callbacks representing the callback configurations for the Connector
     */
    @JsonProperty
    private List<Callback> callbacks;

    /**
     * A list of phone numbers representing the phone numbers associated to the Connector. Max 50 listed in response.
     */
    @JsonProperty("phone_numbers")
    private List<PhoneNumber> phoneNumbers;

    /**
     * A boolean that is true if the Connector has more than 50 associated numbers
     */
    @JsonProperty("more_numbers")
    private boolean moreNumbers;

    /**
     * Quantity of pages for pagination
     */
    @JsonIgnore
    private int pages;

    /**
     * Quantity of connectors in the list
     */
    @JsonIgnore
    private int count;

    /**
     * Next page, if there are any
     */
    @JsonIgnore
    private int nextPage;

    /**
     * Previous page, if there are any
     */
    @JsonIgnore
    private int previousPage;

    /**
     * Current page
     */
    @JsonIgnore
    private int currentPage = 1;

    static {
        classesWithoutRootValue.add(ConnectorCollection.class);
        classesWithoutRootValue.add(ConnectorPaginatedPhoneNumbers.class);
    }

    /**
     * Search for a {@link Connector} by its ID using the API.
     * <p>
     * If the {@link Connector} is not found, a VivialConnectException will be thrown.
     *
     * @param connectorId the id of the connector to look up
     * @return the Connector that was found given the id
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     * @see #getConnectors()
     */
    public static Connector getConnectorById(int connectorId) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        return request(RequestMethod.GET, classURLWithSuffix(Connector.class, String.valueOf(connectorId)), null, null, Connector.class);
    }

    /**
     * Gets all connectors associated with the current account. If there are none, the method will return an empty {@link List}
     *
     * @return a list of connectors
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     * @see #getConnectorById(int)
     */
    public static List<Connector> getConnectors() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        return request(RequestMethod.GET, classURL(Connector.class), null, null, ConnectorCollection.class).getConnectors();
    }

    /**
     * Total number of connectors in the account specified. If there are none, this method will return <code>0</code>.
     *
     * @return connector count
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    public static int count() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        return request(RequestMethod.GET, classURLWithSuffix(Connector.class, "count"), null, null, ResourceCount.class).getCount();
    }

    /**
     * Creates a new Connector resource for the account
     *
     * @return the Connector that was created
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    public Connector create() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        JsonBodyBuilder builder = JsonBodyBuilder.forClass(Connector.class)
                .addParamPair("name", getName());

        Connector createdConnector = request(RequestMethod.POST, classURL(Connector.class),
                builder.build(), null, Connector.class);

        updateObjectState(createdConnector);

        return this;
    }

    /**
     * Updates this connector.
     * <p>
     * The only property available for update is <tt>name</tt>. Therefore, be sure to call {@link #setName(String)}
     * before calling this method if you want to update it. The <tt>id</tt> property should also be set.
     * <p>
     * Keep in mind that this method does not update neither the callbacks nor the phone numbers. To do that, you have
     * to call {@link #updateCallbacks()} and/or {@link #updateAssociatedPhoneNumbers()}, which use separate resource
     * endpoints of their own.
     * <p>
     * If the connector you're trying to update does not exist, a {@link VivialConnectException}
     * holding a 404 response code will be thrown.
     *
     * @return this instance of {@link Connector} with the updated name
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    public Connector update() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        JsonBodyBuilder builder = JsonBodyBuilder.forClass(Connector.class)
                .addParamPair("id", getId())
                .addParamPair("name", getName());

        Connector updatedConnector = request(RequestMethod.PUT, classURLWithSuffix(Connector.class, String.valueOf(getId())),
                builder.build(), null, Connector.class);

        updateObjectState(updatedConnector);
        return this;
    }


    private void updateObjectState(Connector connector) {
        this.id = connector.getId();
        this.dateCreated = connector.getDateCreated();
        this.dateModified = connector.getDateModified();
        this.accountId = connector.getAccountId();
        this.active = connector.isActive();
        this.callbacks = connector.getCallbacks();
        this.name = connector.getName();
        this.phoneNumbers = connector.getPhoneNumbers();
        this.moreNumbers = connector.isMoreNumbers();
    }


    /**
     * Deletes this connector.
     * <p>
     * Returns <code>true</code> if the connector was successfully deleted.
     * <p>
     * If the contact you're trying to delete does not exist, a {@link VivialConnectException}
     * holding a 404 response code will be thrown.
     *
     * @return a boolean value, indicating whether the contact was deleted or not
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    public boolean delete() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        try {
            request(RequestMethod.DELETE, classURLWithSuffix(Connector.class, String.valueOf(getId())), null, null, String.class);
        } catch (NoContentException nce) {
            return true;
        }

        return false;
    }

    /**
     * Unique identifier of the Connector object.
     *
     * @return Connector's object ID
     */
    public int getId() {
        return id;
    }

    /**
     * Set unique identifier of the Connector object.
     *
     * @param id Connector ID value
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set creation date of the connector
     *
     * @return creation date of the connector
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * Set creation date of the connector
     *
     * @param dateCreated creation date value
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Last modification date of the connector
     *
     * @return Last modification date of the connector
     */
    @Override
    public Date getDateModified() {
        return dateModified;
    }

    /**
     * Count of phone numbers that belongs to this connector
     *
     * @return count of phone numbers for this connector
     */
    @Override
    public int getPhoneNumbersCount() {
        return count;
    }

    /**
     * Set phone number count for the connector
     *
     * @param count count value
     */
    public void setPhoneNumbersCount(int count) {
        this.count = count;
    }

    /**
     * Move forward to the next page
     *
     * @return next page value
     * @throws VivialConnectException if an error occurs at API level
     */
    @Override
    public int nextPage() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {

        if (currentPage < pages)
            currentPage++;

        ConnectorWithPhoneNumbers connector = paginate(currentPage);
        mergePhoneNumberFields(connector);

        return currentPage;
    }

    /**
     * Move backward to the previous page
     *
     * @return previous page value
     * @throws VivialConnectException if an error occurs at API level
     */
    @Override
    public int previousPage() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {

        if (currentPage > 1)
            currentPage--;

        ConnectorWithPhoneNumbers connector = paginate(currentPage);
        mergePhoneNumberFields(connector);

        return currentPage;

    }

    /**
     * Next page value returned by the API
     *
     * @return next page value
     */
    @Override
    public int getNextPage() {
        return nextPage;
    }

    /**
     * Set next page value
     *
     * @param next next page value
     */
    public void setNextPage(int next) {
        this.nextPage = next;
    }

    /**
     * Previous page returned by the API
     *
     * @return previous page value
     */
    @Override
    public int getPreviousPage() {
        return previousPage;
    }

    /**
     * Set the previous page value
     *
     * @param previous previous page value
     */
    public void setPreviousPage(int previous) {
        this.previousPage = previous;
    }

    /**
     * Quantity of pages returned by the API
     *
     * @return pages count value
     */
    @Override
    public int getPages() {
        return pages;
    }

    /**
     * Set pages count
     *
     * @param pages pages count value
     */
    public void setPages(int pages) {
        this.pages = pages;
    }

    /**
     * Last modification value of the connector
     *
     * @param dateModified Connector's modification value
     */
    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    /**
     * User's account ID
     *
     * @return account ID value
     */
    public int getAccountId() {
        return accountId;
    }

    /**
     * Set user's account ID
     *
     * @param accountId account ID value
     */
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    /**
     * Determine if the connector is enabled or not.
     *
     * @return connector active value
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Set active value for this connector
     *
     * @param active active value
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * User defined descriptive label
     *
     * @return connector's name value
     */
    public String getName() {
        return name;
    }

    /**
     * Set connector's name
     *
     * @param name connector's name value
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * A list of callbacks representing the callback configurations for the Connector.
     *
     * @return list of callbacks
     */
    @Override
    public List<Callback> getCallbacks() {
        return callbacks;
    }

    /**
     * Set connector's callbacks
     *
     * @param callbacks list of callbacks
     */
    public void setCallbacks(List<Callback> callbacks) {
        this.callbacks = callbacks;
    }

    /**
     * Use this method to add callbacks which can later be saved to the server using {@link Connector#createCallbacks()}
     * or {@link Connector#updateCallbacks()}. This method call can be chained in a builder-like fashion, like so:
     * <pre>
     * <code>
     * class CallbackCreator {
     *
     *  public void addAllCallbacks(Connector connector) {
     *    Callback c1 = new Callback();
     *    c1.setMessageType("text");
     *    c1.setEventType("incoming");
     *
     *    Callback c2 = new Callback();
     *    c2.setMessageType("text");
     *    c2.setEventType("status");
     *
     *    connector.addCallback(c1).addCallback(c2).createCallbacks();
     *  }
     * }
     * </code>
     * </pre>
     *
     * @param callback the callback to be added
     * @return this instance of {@link Connector}
     * @see Callback
     * @see Connector#setCallbacks(java.util.List)
     * @see Connector#createCallbacks()
     * @see Connector#updateCallbacks()
     */
    public Connector addCallback(Callback callback) {
        if (callbacks == null) {
            callbacks = new ArrayList<Callback>();
        }

        callbacks.add(callback);

        return this;
    }

    /**
     * Overwrites and associates the list of callbacks added through {@link Connector#addCallback(net.vivialconnect.model.connector.Callback)} and
     * {@link Connector#setCallbacks(java.util.List)} to this connector.
     * <p>
     * Returns an instance of the {@link ConnectorWithCallbacks} interface, which can be used to access the newly-created callbacks.
     * <p>
     * For more details, go to the VivialConnect API documentation's
     * <a href="https://www.vivialconnect.net/docs/api.html#post--api-v1.0-accounts-(int-account_id)-connectors-(int-connector_id)-callbacks.json">callback section</a>.
     *
     * @return an instance {@link ConnectorWithCallbacks} holding the list of created callbacks
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     * @see Callback
     * @see Connector#addCallback(net.vivialconnect.model.connector.Callback)
     * @see Connector#setCallbacks(java.util.List)
     */
    public ConnectorWithCallbacks createCallbacks() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        JsonBodyBuilder builder = JsonBodyBuilder.forClass(Connector.class)
                .addParamPair("callbacks", callbacks);

        Connector connectorWithCallbacks = request(RequestMethod.POST, classURLWithSuffix(Connector.class, String.format("%d/callbacks", getId())),
                builder.build(), null, Connector.class);
        mergeCallbackFields(connectorWithCallbacks);

        return this;
    }

    /**
     * Updates the list of callbacks, editing any existing ones and adding any new ones, associated to
     * this connector. Here's an example of how to edit an existing callback:
     * <pre>
     * <code>
     * class CallbackUpdate {
     *
     *  public void updateCallback(Connector connector, String newMessageType) {
     *    Callback callback = connector.getCallbacks().get(0);
     *    callback.setMesssageType(newMessageType);
     *
     *    connector.updateCallbacks();
     *  }
     * }
     * </code>
     * </pre>
     * Returns an instance of the {@link ConnectorWithCallbacks} interface, which can be used to access the updated callbacks.
     *
     * @return an instance {@link ConnectorWithCallbacks} holding the list of updated callbacks
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     * @see Callback
     * @see Connector#addCallback(net.vivialconnect.model.connector.Callback)
     * @see Connector#setCallbacks(java.util.List)
     */
    public ConnectorWithCallbacks updateCallbacks() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        JsonBodyBuilder builder = JsonBodyBuilder.forClass(Connector.class)
                .addParamPair("callbacks", callbacks);

        Connector connectorWithCallbacks = request(RequestMethod.PUT, classURLWithSuffix(Connector.class, String.format("%d/callbacks", getId())),
                builder.build(), null, Connector.class);
        mergeCallbackFields(connectorWithCallbacks);

        return this;
    }


    private void mergeCallbackFields(ConnectorWithCallbacks connectorWithCallbacks) {
        this.dateModified = connectorWithCallbacks.getDateModified();
        this.callbacks = connectorWithCallbacks.getCallbacks();
    }

    /**
     * Removes all the callbacks associated to this connector.
     *
     * @return an instance {@link ConnectorWithCallbacks} holding an empty list of callbacks
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     * @see Callback
     * @see Connector#addCallback(net.vivialconnect.model.connector.Callback)
     * @see Connector#setCallbacks(java.util.List)
     */
    public ConnectorWithCallbacks deleteAllCallbacks() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        return deleteCallbacks(this.callbacks);
    }

    /**
     * Removes a single callback.
     *
     * @param callback the callback to be removed
     * @return an instance {@link ConnectorWithCallbacks} holding the updated list of callbacks
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     * @see Callback
     */
    public ConnectorWithCallbacks deleteSingleCallback(Callback callback) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        List<Callback> singleCallbackList = new ArrayList<Callback>(1);
        singleCallbackList.add(callback);

        return deleteCallbacks(singleCallbackList);
    }

    /**
     * Removes a series of callbacks
     *
     * @param callbacks the callbacks to be removed
     * @return an instance {@link ConnectorWithCallbacks} holding the updated list of callbacks
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     * @see Callback
     */
    public ConnectorWithCallbacks deleteCallbacks(List<Callback> callbacks) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        JsonBodyBuilder builder = JsonBodyBuilder.forClass(Connector.class)
                .addParamPair("callbacks", callbacks);

        return request(RequestMethod.DELETE, classURLWithSuffix(Connector.class, String.format("%d/callbacks", getId())),
                builder.build(), null, Connector.class);
    }

    /**
     * List of phone numbers associated to Connector
     *
     * @return list of numbers associated to the connector
     */
    @Override
    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    /**
     * Set a list of phone numbers to this connector
     *
     * @param phoneNumbers list of phone numbers
     */
    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    /**
     * Adds a new phone number, given the phoneNumberId and phoneNumber arguments.
     * <p>
     * This method calls {@link Connector#addPhoneNumber(PhoneNumber)} underneath, but the arguments are taken
     * separately.
     *
     * @param phoneNumberId the id of the phone number to be added
     * @param phoneNumber   the phone number to be added
     * @return this instance of {@link Connector}
     * @see PhoneNumber
     * @see Connector#addPhoneNumber(PhoneNumber)
     * @see Connector#setPhoneNumbers(List)
     * @see Connector#associatePhoneNumbers()
     * @see Connector#updateAssociatedPhoneNumbers()
     */
    public Connector addPhoneNumber(int phoneNumberId, String phoneNumber) {
        return addPhoneNumber(new PhoneNumber(phoneNumberId, phoneNumber));
    }

    /**
     * Use this method to add phone numbers which can later be saved to the server using {@link Connector#associatePhoneNumbers()}
     * or {@link Connector#updateAssociatedPhoneNumbers()}. This method call can be chained in a builder-like fashion, like so:
     * <pre>
     * <code>
     * class PhoneNumberCreator {
     *
     *  public void addAllPhoneNumbers(Connector connector) {
     *    PhoneNumber p1 = new PhoneNumber();
     *    p1.setPhoneNumberId(29);
     *    p1.setPhoneNumber("+13022136859");
     *
     *    PhoneNumber p2 = new PhoneNumber();
     *    p2.setPhoneNumberId(30);
     *    p2.setPhoneNumber("+13022136850");
     *
     *    connector.addPhoneNumber(c1).addPhoneNumber(c2).createCallbacks();
     *  }
     * }
     * </code>
     * </pre>
     *
     * @param phoneNumber the phone number to be added
     * @return this instance of {@link Connector}
     * @see PhoneNumber
     * @see Connector#addPhoneNumber(int, String)
     * @see Connector#setPhoneNumbers(List)
     * @see Connector#associatePhoneNumbers()
     * @see Connector#updateAssociatedPhoneNumbers()
     */
    public Connector addPhoneNumber(PhoneNumber phoneNumber) {
        if (phoneNumbers == null) {
            phoneNumbers = new ArrayList<PhoneNumber>();
        }

        this.phoneNumbers.add(phoneNumber);

        return this;
    }

    /**
     * Overwrites and associates the list of phone numbers added through {@link Connector#addPhoneNumber(PhoneNumber)} and
     * {@link Connector#setPhoneNumbers(List)} to this connector.
     * <p>
     * Returns an instance of the {@link ConnectorWithPhoneNumbers} interface, which can be used to access the newly-associated phone numbers.
     * <p>
     * For more details, go to the VivialConnect API documentation's
     * <a href="https://www.vivialconnect.net/docs/api.html#post--api-v1.0-accounts-(int-account_id)-connectors-(int-connector_id)-phone_numbers.json">phone number section</a>.
     *
     * @return an instance {@link ConnectorWithPhoneNumbers} holding the list of associated phone numbers
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     * @see PhoneNumber
     * @see Connector#addPhoneNumber(PhoneNumber)
     * @see Connector#addPhoneNumber(int, String)
     * @see Connector#setPhoneNumbers(List)
     */
    public ConnectorWithPhoneNumbers associatePhoneNumbers() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        JsonBodyBuilder builder = JsonBodyBuilder.forClass(Connector.class)
                .addParamPair("phone_numbers", phoneNumbers);

        ConnectorWithPhoneNumbers connector = request(RequestMethod.POST, classURLWithSuffix(Connector.class, String.format("%d/phone_numbers", getId())),
                builder.build(), null, ConnectorPaginatedPhoneNumbers.class).getConnector();
        mergePhoneNumberFields(connector);

        return this;
    }

    /**
     * Updates the list of phone numbers, editing any existing ones and adding any new ones, associated to
     * this connector. Here's an example of how to edit an associated phone number:
     * <pre>
     * <code>
     * class PhoneNumberUpdate {
     *
     *  public void updatePhoneNumbers(Connector connector) {
     *    connector.addPhoneNumber(31, "+13022136851");
     *    connector.updateAssociatedPhoneNumbers();
     *  }
     * }
     * </code>
     * </pre>
     * Returns an instance of the {@link ConnectorWithPhoneNumbers} interface, which can be used to access the updated phone numbers.
     *
     * @return an instance {@link ConnectorWithPhoneNumbers} holding the list of updated phone numbers
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     * @see PhoneNumber
     * @see Connector#addPhoneNumber(PhoneNumber)
     * @see Connector#addPhoneNumber(int, String)
     * @see Connector#setPhoneNumbers(List)
     */
    public ConnectorWithPhoneNumbers updateAssociatedPhoneNumbers() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        JsonBodyBuilder builder = JsonBodyBuilder.forClass(Connector.class)
                .addParamPair("phone_numbers", phoneNumbers);

        ConnectorWithPhoneNumbers connector = request(RequestMethod.PUT, classURLWithSuffix(Connector.class, String.format("%d/phone_numbers", getId())),
                builder.build(), null, ConnectorPaginatedPhoneNumbers.class).getConnector();
        mergePhoneNumberFields(connector);

        return this;
    }

    /**
     * Removes all the phone numbers associated to this connector.
     *
     * @return an instance {@link ConnectorWithPhoneNumbers} holding an empty list of phone numbers
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     * @see PhoneNumber
     * @see Connector#addPhoneNumber(PhoneNumber)
     * @see Connector#addPhoneNumber(int, String)
     * @see Connector#setPhoneNumbers(List)
     */
    public ConnectorWithPhoneNumbers deleteAllPhoneNumbers() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        return deletePhoneNumbers(this.phoneNumbers);
    }

    /**
     * Removes a single phone number.
     *
     * @param phoneNumber the phone number to be removed
     * @return an instance {@link ConnectorWithPhoneNumbers} holding the updated list of phone numbers
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     * @see PhoneNumber
     */
    public ConnectorWithPhoneNumbers deleteSinglePhoneNumber(PhoneNumber phoneNumber) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        List<PhoneNumber> singlePhoneNumberList = new ArrayList<PhoneNumber>(1);
        singlePhoneNumberList.add(phoneNumber);

        return deletePhoneNumbers(singlePhoneNumberList);
    }

    /**
     * Removes a series of phone numbers
     *
     * @param phoneNumbers the phone numbers to be removed
     * @return an instance {@link ConnectorWithPhoneNumbers} holding the updated list of phone numbers
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     * @see PhoneNumber
     */
    public ConnectorWithPhoneNumbers deletePhoneNumbers(List<PhoneNumber> phoneNumbers) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        JsonBodyBuilder builder = JsonBodyBuilder.forClass(Connector.class)
                .addParamPair("phone_numbers", phoneNumbers);

        ConnectorWithPhoneNumbers connector = request(RequestMethod.DELETE, classURLWithSuffix(Connector.class, String.format("%d/phone_numbers", getId())),
                builder.build(), null, ConnectorPaginatedPhoneNumbers.class).getConnector();
        mergePhoneNumberFields(connector);

        return this;
    }


    private void mergePhoneNumberFields(ConnectorWithPhoneNumbers connectorWithPhoneNumbers) {

        this.dateModified = connectorWithPhoneNumbers.getDateModified();
        this.phoneNumbers = connectorWithPhoneNumbers.getPhoneNumbers();
        this.count = connectorWithPhoneNumbers.getPhoneNumbersCount();
        this.previousPage = connectorWithPhoneNumbers.getPreviousPage();
        this.nextPage = connectorWithPhoneNumbers.getNextPage();
        this.pages = connectorWithPhoneNumbers.getPages();

    }

    private ConnectorWithPhoneNumbers paginate(int toPage) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("page", String.valueOf(toPage));

        ConnectorWithPhoneNumbers connector = request(RequestMethod.GET, classURLWithSuffix(Connector.class, String.format("%d/phone_numbers", getId())),
                null, queryParams, ConnectorPaginatedPhoneNumbers.class).getConnector();

        return connector;
    }

    /**
     * A boolean that is true if the Connector has more than 50 associated numbers.
     *
     * @return true if connector has more that 50 numbers or false if the connector has less that 50
     */
    public boolean isMoreNumbers() {
        return moreNumbers;
    }

    /**
     * Set if connector has more that 50 numbers
     *
     * @param moreNumbers boolean value
     */
    public void setMoreNumbers(boolean moreNumbers) {
        this.moreNumbers = moreNumbers;
    }

    /**
     * Pagination current page
     *
     * @return current page
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * Set current page
     *
     * @param currentPage current page value
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

}
