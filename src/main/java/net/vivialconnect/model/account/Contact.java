package net.vivialconnect.model.account;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import net.vivialconnect.model.ResourceCount;
import net.vivialconnect.model.VivialConnectResource;
import net.vivialconnect.model.error.NoContentException;
import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.format.JsonBodyBuilder;

/**
 * This class contains information of account users and administrators.
 */
@JsonRootName("contact")
public class Contact extends VivialConnectResource {

    private static final long serialVersionUID = 3140451099385557777L;

    private static String[] REQUIRED_FIELDS = {"firstName", "lastName", "email", "contactType"};

    /**
     * Unique identifier of the contact object
     */
    @JsonProperty
    private int id;

    /**
     * Creation date (UTC) of the contact in ISO 8601 format
     */
    @JsonProperty("date_created")
    private Date dateCreated;

    /**
     * Last modification date (UTC) of the contact in ISO 8601 format
     */
    @JsonProperty("date_modified")
    private Date dateModified;

    /**
     * Unique identifier of the parent account
     */
    @JsonProperty("account_id")
    private int accountId;

    @JsonProperty
    private boolean active;

    @JsonProperty
    private String address1;

    @JsonProperty
    private String address2;

    @JsonProperty
    private String address3;

    @JsonProperty
    private String city;

    /**
     * Name of company
     */
    @JsonProperty("company_name")
    private String companyName;

    /**
     * The type of contact. Can be one of: ‘agency,’ ‘billing,’ ‘main,’ ‘company,’ or ‘marketing.’
     */
    @JsonProperty("contact_type")
    private String contactType;

    @JsonProperty
    private String country;

    /**
     * Contacts's email address
     */
    @JsonProperty
    private String email;

    /**
     * Contacts's fax number
     */
    @JsonProperty
    private String fax;

    /**
     * Contacts's first name
     */
    @JsonProperty("first_name")
    private String firstName;

    /**
     * Contacts's last name
     */
    @JsonProperty("last_name")
    private String lastName;

    /**
     * Contacts's mobile phone number
     */
    @JsonProperty("mobile_phone")
    private String mobilePhone;

    /**
     * Contacts's postal code
     */
    @JsonProperty("postal_code")
    private String postalCode;

    @JsonProperty
    private String state;

    /**
     * Contacts's position or title
     */
    @JsonProperty
    private String title;

    /**
     * Contacts's work phone number
     */
    @JsonProperty("work_phone")
    private String workPhone;

    private JsonBodyBuilder jsonBodyBuilder;

    static {
        classesWithoutRootValue.add(ContactCollection.class);
    }

    public Contact() {
        jsonBodyBuilder = JsonBodyBuilder.forClass(Contact.class);
    }

    /**
     * Create a new contact based in the properties of another contact object.
     *
     * @param contact a contact object
     */
    public Contact(Contact contact) {
        jsonBodyBuilder = JsonBodyBuilder.forClass(Contact.class).addParamPair("id", contact.getId());
        updateObjectState(contact);
    }

    /**
     * Creates a new contact using the provided fields.
     * <p>
     * This method throws an {@link IllegalStateException} if any of the required fields
     * are not set.
     * <p>
     *
     * @return this instance of {@link Contact} with the newly-created properties
     * @throws VivialConnectException if there is an API-level error
     * @see Contact#update()
     */
    public Contact create() throws VivialConnectException {
        verifyRequiredFields();
        Contact createdContact = request(RequestMethod.POST, classURL(Contact.class),
                jsonBodyForCreate(), null, Contact.class);

        updateObjectState(createdContact);

        return this;
    }


    private void verifyRequiredFields() {
        Class<?> c = getClass();
        for (int i = 0; i < REQUIRED_FIELDS.length; i++) {
            String requiredFieldName = REQUIRED_FIELDS[i];
            Field requiredField = getDeclaredField(c, requiredFieldName);

            if (requiredField == null) {
                continue;
            }

            if (requiredField.getType().equals(String.class)) {
                String fieldValue = (String) getValueFromField(requiredField);
                validateStringValue(requiredField.getName(), fieldValue);
            }
        }
    }


    private Field getDeclaredField(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (SecurityException e) {
        } catch (NoSuchFieldException e) {
        }

        return null;
    }


    private Object getValueFromField(Field field) {
        try {
            return field.get(this);
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }

        return null;
    }


    private void validateStringValue(String fieldName, String fieldValue) {
        if (fieldValue == null || fieldValue.isEmpty()) {
            throw new IllegalStateException(String.format("Parameter '%s' is null or empty", fieldName));
        }
    }


    private String jsonBodyForCreate() {
        JsonBodyBuilder builder = JsonBodyBuilder.forClass(Contact.class);
        addRequiredFields(builder);
        addOptionalFields(builder);

        return builder.build();
    }


    private void addRequiredFields(JsonBodyBuilder builder) {
        /* TODO: Move all these to class-level constants */
        ifParamValidAddToBuilder(builder, "first_name", getFirstName());
        ifParamValidAddToBuilder(builder, "last_name", getLastName());
        ifParamValidAddToBuilder(builder, "email", getEmail());
        ifParamValidAddToBuilder(builder, "contact_type", getContactType());
    }


    private void addOptionalFields(JsonBodyBuilder builder) {
        ifParamValidAddToBuilder(builder, "company_name", getCompanyName());
        ifParamValidAddToBuilder(builder, "title", getTitle());
        ifParamValidAddToBuilder(builder, "address1", getAddress1());
        ifParamValidAddToBuilder(builder, "address2", getAddress2());
        ifParamValidAddToBuilder(builder, "address3", getAddress3());
        ifParamValidAddToBuilder(builder, "city", getCity());
        ifParamValidAddToBuilder(builder, "state", getState());
        ifParamValidAddToBuilder(builder, "postal_code", getPostalCode());
        ifParamValidAddToBuilder(builder, "country", getState());
        ifParamValidAddToBuilder(builder, "mobile_phone", getMobilePhone());
        ifParamValidAddToBuilder(builder, "work_phone", getWorkPhone());
    }


    private void updateObjectState(Contact contact) {
        this.id = contact.getId();
        this.accountId = contact.getAccountId();
        this.active = contact.isActive();
        this.address1 = contact.getAddress1();
        this.address2 = contact.getAddress2();
        this.address3 = contact.getAddress3();
        this.city = contact.getCity();
        this.companyName = contact.getCompanyName();
        this.contactType = contact.getContactType();
        this.country = contact.getCountry();
        this.dateCreated = contact.getDateCreated();
        this.dateModified = contact.getDateModified();
        this.email = contact.getEmail();
        this.fax = contact.getFax();
        this.firstName = contact.getFirstName();
        this.lastName = contact.getLastName();
        this.mobilePhone = contact.getMobilePhone();
        this.postalCode = contact.getPostalCode();
        this.state = contact.getState();
        this.title = contact.getTitle();
        this.workPhone = contact.getWorkPhone();

        ifParamValidAddToBuilder(jsonBodyBuilder, "id", getId());
    }

    /**
     * Updates this contact by sending to the server the properties that were changed via
     * this class's set methods.
     * <p>
     * If the contact you're trying to update does not exist, a {@link VivialConnectException}
     * holding a 404 response code will be thrown.
     *
     * @return this instance of {@link Contact} with the updated properties
     * @throws VivialConnectException if there is an API-level error
     */
    public Contact update() throws VivialConnectException {
        verifyRequiredFields();
        Contact updatedContact = request(RequestMethod.PUT, classURLWithSuffix(Contact.class, String.valueOf(getId())),
                jsonBodyForUpdate(), null, Contact.class);
        updateObjectState(updatedContact);
        return this;
    }


    public String jsonBodyForUpdate() {
        return jsonBodyBuilder.build();
    }

    /**
     * Deletes this contact from the database, dissociating it from the account.
     * <p>
     * If the contact you're trying to delete does not exist, a {@link VivialConnectException}
     * holding a 404 response code will be thrown.
     * <p>
     *
     * @return a boolean value, indicating whether the contact was deleted or not
     * @throws VivialConnectException if there is an API-level error
     */
    public boolean delete() throws VivialConnectException {
        try {
            request(RequestMethod.DELETE, classURLWithSuffix(Contact.class, String.valueOf(getId())), null, null, String.class);
        } catch (NoContentException nce) {
            return true;
        }

        return false;
    }

    /**
     * Gets all contacts for this Account using the API.
     * <p>
     * If no Contact were found for this {@link Account}, a VivialConnectException will be thrown.
     *
     * @return a list of contacts
     * @throws VivialConnectException if there is an API-level error
     * @see #getContactById(int)
     * @see #getContacts(Map)
     */
    public static List<Contact> getContacts() throws VivialConnectException {
        return getContacts(null);
    }

    /**
     * Search and filter every contact for this Account using the API.
     * <p>
     * If no {@link Contact} were found for this {@link Account}, a VivialConnectException will be thrown.
     *
     * @param queryParams a map of {@link String } key-value pairs used to filter results, possible values are:
     *                    <p>
     *                    <code>page</code> – Page number within the returned list of accounts. Default value: 1.
     *                    <p>
     *                    <code>limit</code> – Number of results to return per page. Default value: 50. Maximum value: 150.
     * @return a list of contact
     * @throws VivialConnectException if there is an API-level error
     * @see #getContacts()
     * @see #getContactById(int)
     */
    public static List<Contact> getContacts(Map<String, String> queryParams) throws VivialConnectException {
        return request(RequestMethod.GET, classURL(Contact.class), null, queryParams, ContactCollection.class).getContacts();
    }

    /**
     * Search for a {@link Contact} by its ID using the API.
     * <p>
     * If the {@link Contact} is not found, a VivialConnectException will be thrown.
     *
     * @param contactId the id of the user to look up
     * @return the Contact that was found given the id
     * @throws VivialConnectException if there is an API-level error
     * @see #getContacts()
     * @see #getContacts(Map)
     */
    public static Contact getContactById(int contactId) throws VivialConnectException {
        return new Contact(request(RequestMethod.GET, classURLWithSuffix(Contact.class, String.valueOf(contactId)), null, null, Contact.class));
    }

    /**
     * Total number of contacts in the account specified. If there are none, this method will return <code>0</code>.
     *
     * @return contact count
     * @throws VivialConnectException if there is an API-level error
     */
    public static int count() throws VivialConnectException {
        return request(RequestMethod.GET, classURLWithSuffix(Contact.class, "count"), null, null, ResourceCount.class).getCount();
    }

    /**
     * Unique Identifier of the contact object
     *
     * @return contact object ID
     */
    public int getId() {
        return id;
    }

    /**
     * Set the ID of the contact object
     *
     * @param id contact ID value
     */
    public void setId(int id) {
        this.id = id;
        ifParamValidAddToBuilder(jsonBodyBuilder, "id", getId());
    }

    /**
     * Creation date of the contact
     *
     * @return creation date of the contact
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * Set the creation date of the contact
     *
     * @param dateCreated creation date value
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Modified date of the contact
     *
     * @return modified date of the contact
     */
    public Date getDateModified() {
        return dateModified;
    }

    /**
     * Set the modified date of the contact
     *
     * @param dateModified modified date value
     */
    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    /**
     * User's account ID of the contact
     *
     * @return user account ID
     */
    public int getAccountId() {
        return accountId;
    }

    /**
     * Set user's account ID
     *
     * @param accountId user account ID value
     */
    public void setAccountId(int accountId) {
        this.accountId = accountId;
        ifParamValidAddToBuilder(jsonBodyBuilder, "account_id", getAccountId());
    }

    /**
     * Contact active
     *
     * @return true if the contact is active, false if not
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Set the active value of the contact
     *
     * @param active boolean value
     */
    public void setActive(boolean active) {
        this.active = active;
        jsonBodyBuilder.addParamPair("active", isActive());
    }

    /**
     * Address No.1 of the contact
     *
     * @return address of the contact
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * Set address No.1 of the contact
     *
     * @param address1 address value
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
        ifParamValidAddToBuilder(jsonBodyBuilder, "address1", getAddress1());
    }

    /**
     * Address No.2 of the contact
     *
     * @return address of the contact
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * Set address No.2 of the contact
     *
     * @param address2 address value
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
        ifParamValidAddToBuilder(jsonBodyBuilder, "address2", getAddress2());
    }

    /**
     * Address No.3 of the contact
     *
     * @return address of the contact
     */
    public String getAddress3() {
        return address3;
    }

    /**
     * Set address No.3 of the contact
     *
     * @param address3 address value
     */
    public void setAddress3(String address3) {
        this.address3 = address3;
        ifParamValidAddToBuilder(jsonBodyBuilder, "address3", getAddress3());
    }

    /**
     * Contact's city
     *
     * @return Contact's city value
     */
    public String getCity() {
        return city;
    }

    /**
     * Set city of the contact
     *
     * @param city city value
     */
    public void setCity(String city) {
        this.city = city;
        ifParamValidAddToBuilder(jsonBodyBuilder, "city", getCity());
    }

    /**
     * Company name for the contact
     *
     * @return company name value
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Set the company name for the contact
     *
     * @param companyName company name value
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
        ifParamValidAddToBuilder(jsonBodyBuilder, "company_name", getCompanyName());
    }

    /**
     * Contact's type.  Can be one of: ‘agency,’ ‘billing,’ ‘main,’ ‘company,’ or ‘marketing.’
     *
     * @return contact's type value
     */
    public String getContactType() {
        return contactType;
    }

    /**
     * Set the contact type.  Can be one of: ‘agency,’ ‘billing,’ ‘main,’ ‘company,’ or ‘marketing’
     *
     * @param contactType one of : ‘agency,’ ‘billing,’ ‘main,’ ‘company,’ or ‘marketing’
     */
    public void setContactType(String contactType) {
        this.contactType = contactType;
        ifParamValidAddToBuilder(jsonBodyBuilder, "contact_type", getContactType());
    }

    /**
     * Contact’s address country
     *
     * @return address country value
     */
    public String getCountry() {
        return country;
    }

    /**
     * Set contact’s address country
     *
     * @param country country value
     */
    public void setCountry(String country) {
        this.country = country;
        ifParamValidAddToBuilder(jsonBodyBuilder, "country", getCountry());
    }

    /**
     * The contact’s email address
     *
     * @return contact's email address value
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set contact’s email address
     *
     * @param email email value
     */
    public void setEmail(String email) {
        this.email = email;
        ifParamValidAddToBuilder(jsonBodyBuilder, "email", getEmail());
    }

    /**
     * The contact's fax number
     *
     * @return contact's fax number value
     */
    public String getFax() {
        return fax;
    }

    /**
     * Set contact's fax number
     *
     * @param fax fax number value
     */
    public void setFax(String fax) {
        this.fax = fax;
        ifParamValidAddToBuilder(jsonBodyBuilder, "fax", getFax());
    }

    /**
     * Contacts’s first name
     *
     * @return contact's first name value
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set contacts’s first name. Max. length: 128 characters.
     *
     * @param firstName first name value
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
        ifParamValidAddToBuilder(jsonBodyBuilder, "first_name", getFirstName());
    }

    /**
     * Contacts’s last name
     *
     * @return contact's last name value
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set contact's last name
     *
     * @param lastName last name value
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
        ifParamValidAddToBuilder(jsonBodyBuilder, "last_name", getLastName());
    }

    /**
     * Contact's mobile phone number
     *
     * @return conta
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * Set contact's mobile phone number
     *
     * @param mobilePhone mobile phone number value
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        ifParamValidAddToBuilder(jsonBodyBuilder, "mobile_phone", getMobilePhone());
    }

    /**
     * Contact's postal code
     *
     * @return Contact's postal code value
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Contact's postal code
     *
     * @param postalCode postal code value
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        ifParamValidAddToBuilder(jsonBodyBuilder, "postal_code", getPostalCode());
    }

    /**
     * Contact's state name
     *
     * @return contact's state value
     */
    public String getState() {
        return state;
    }

    /**
     * Set contact's state name
     *
     * @param state state name value
     */
    public void setState(String state) {
        this.state = state;
        ifParamValidAddToBuilder(jsonBodyBuilder, "state", getState());
    }

    /**
     * Contact’s position or title
     *
     * @return Contact’s title value
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set contact’s position or title
     *
     * @param title title value
     */
    public void setTitle(String title) {
        this.title = title;
        ifParamValidAddToBuilder(jsonBodyBuilder, "title", getTitle());
    }

    /**
     * Contact's phone number
     *
     * @return Contact's phone number value
     */
    public String getWorkPhone() {
        return workPhone;
    }

    /**
     * Set Contact's phone number
     *
     * @param workPhone work phone number value
     */
    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
        ifParamValidAddToBuilder(jsonBodyBuilder, "work_phone", getWorkPhone());
    }
}