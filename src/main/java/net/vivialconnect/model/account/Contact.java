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

@JsonRootName("contact")
public class Contact extends VivialConnectResource{

    private static final long serialVersionUID = 3140451099385557777L;

    private static String[] REQUIRED_FIELDS = { "firstName", "lastName", "email", "contactType" };

    /** Unique identifier of the user object */
    @JsonProperty
    private int id;

    /** Creation date (UTC) of the contact in ISO 8601 format */
    @JsonProperty("date_created")
    private Date dateCreated;

    /** Last modification date (UTC) of the contact in ISO 8601 format */
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

    /** Name of company */
    @JsonProperty("company_name")
    private String companyName;

    /** The type of contact. Can be one of: ‘agency,’ ‘billing,’ ‘main,’ ‘company,’ or ‘marketing.’ */
    @JsonProperty("contact_type")
    private String contactType;

    @JsonProperty
    private String country;

    /** Contacts's email address */
    @JsonProperty
    private String email;

    /** Contacts's fax number */
    @JsonProperty
    private String fax;

    /** Contacts's first name */
    @JsonProperty("first_name")
    private String firstName;

    /** Contacts's last name */
    @JsonProperty("last_name")
    private String lastName;

    /** Contacts's mobile phone number */
    @JsonProperty("mobile_phone")
    private String mobilePhone;

    /** Contacts's postal code */
    @JsonProperty("postal_code")
    private String postalCode;

    @JsonProperty
    private String state;

    /** Contacts's position or title */
    @JsonProperty
    private String title;

    /** Contacts's work phone number */
    @JsonProperty("work_phone")
    private String workPhone;

    private JsonBodyBuilder jsonBodyBuilder;

    static {
        classesWithoutRootValue.add(ContactCollection.class);
    }

    public Contact(){
        jsonBodyBuilder = JsonBodyBuilder.forClass(Contact.class);
    }


    public Contact(Contact contact){
        jsonBodyBuilder = JsonBodyBuilder.forClass(Contact.class).addParamPair("id", contact.getId());
        updateObjectState(contact);
    }

    /**
     * Creates a new contact using the provided fields.
     * <p>
     * This method throws an {@link IllegalStateException} if any of the required fields
     * are not set.
     * <p>
     * For more details on which fields are required, refer to the VivialConnect API documentation's
     * <a href="https://www.vivialconnect.net/docs/api.html#post--api-v1.0-accounts-(int-account_id)-contacts.json">contact section.</a>
     * 
     * @return this instance of {@link Contact} with the newly-created properties
     * @throws VivialConnectException if there is an API-level error
     * 
     * @see Contact#update()
     */
    public Contact create() throws VivialConnectException{
        verifyRequiredFields();
        Contact createdContact = request(RequestMethod.POST, classURL(Contact.class),
                                        jsonBodyForCreate(), null, Contact.class);

        updateObjectState(createdContact);
        
        return this;
    }


    private void verifyRequiredFields(){
        Class<?> c = getClass();
        for (int i = 0; i < REQUIRED_FIELDS.length; i++){
            String requiredFieldName = REQUIRED_FIELDS[i];
            Field requiredField = getDeclaredField(c, requiredFieldName);

            if (requiredField == null){
                continue;
            }

            if (requiredField.getType().equals(String.class)){
                String fieldValue = (String) getValueFromField(requiredField);
                validateStringValue(requiredField.getName(), fieldValue);
            }
        }
    }


    private Field getDeclaredField(Class<?> clazz, String fieldName){
        try{
            return clazz.getDeclaredField(fieldName);
        }
        catch (SecurityException e) {}
        catch (NoSuchFieldException e) {}

        return null;
    }


    private Object getValueFromField(Field field){
        try{
            return field.get(this);
        }
        catch (IllegalArgumentException e) {}
        catch (IllegalAccessException e) {}

        return null;
    }


    private void validateStringValue(String fieldName, String fieldValue){
        if (fieldValue == null || fieldValue.isEmpty()){
            throw new IllegalStateException(String.format("Parameter '%s' is null or empty", fieldName));
        }
    }


    private String jsonBodyForCreate(){
        JsonBodyBuilder builder = JsonBodyBuilder.forClass(Contact.class);
        addRequiredFields(builder);
        addOptionalFields(builder);

        return builder.build();
    }


    private void addRequiredFields(JsonBodyBuilder builder){
        /* TODO: Move all these to class-level constants */
        ifParamValidAddToBuilder(builder, "first_name", getFirstName());
        ifParamValidAddToBuilder(builder, "last_name", getLastName());
        ifParamValidAddToBuilder(builder, "email", getEmail());
        ifParamValidAddToBuilder(builder, "contact_type", getContactType());
    }


    private void addOptionalFields(JsonBodyBuilder builder){
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


    private void updateObjectState(Contact contact){
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
     * For more details on which properties can be updated, refer to the VivialConnect API documentation's
     * <a href="https://www.vivialconnect.net/docs/api.html#put--api-v1.0-accounts-(int-account_id)-contacts-(int-id).json">contact section.</a>
     * <p>
     * If the contact you're trying to update does not exist, a {@link VivialConnectException}
     * holding a 404 response code will be thrown.
     * 
     * @return this instance of {@link Contact} with the updated properties
     * @throws VivialConnectException if there is an API-level error
     */
    public Contact update() throws VivialConnectException{
        verifyRequiredFields();
        Contact updatedContact = request(RequestMethod.PUT, classURLWithSuffix(Contact.class, String.valueOf(getId())),
                                        jsonBodyForUpdate(), null, Contact.class);
        updateObjectState(updatedContact);
        return this;
    }


    public String jsonBodyForUpdate(){
        return jsonBodyBuilder.build();
    }

    /**
     * Deletes this contact from the database, dissociating it from the account.
     * <p>
     * If the contact you're trying to delete does not exist, a {@link VivialConnectException}
     * holding a 404 response code will be thrown.
     * <p>
     * Returns <code>true</code> if the connector was successfully deleted.
     * 
     * @return a boolean value, indicating whether the contact was deleted or not
     * @throws VivialConnectException if there is an API-level error
     *
     */
    public boolean delete() throws VivialConnectException{
        try{
            request(RequestMethod.DELETE, classURLWithSuffix(Contact.class, String.valueOf(getId())), null, null, String.class);
        }catch(NoContentException nce){
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
     * 
     * @see #getContactById(int)
     * @see #getContacts(Map) 
     */
    public static List<Contact> getContacts() throws VivialConnectException{
        return getContacts(null);
    }

    /**
     * Search and filter every contact for this Account using the API.
     * <p>
     * If no {@link Contact} were found for this {@link Account}, a VivialConnectException will be thrown.
     * 
     * @param queryParams a map of {@link String } key-value pairs used to filter results, possible values are:
     * <p>
     * <code>page</code> – Page number within the returned list of accounts. Default value: 1.
     * <p>
     * <code>limit</code> – Number of results to return per page. Default value: 50. Maximum value: 150.
     * @return a list of contact
     * @throws VivialConnectException if there is an API-level error
     * 
     * @see #getContacts()
     * @see #getContactById(int) 
     */
    public static List<Contact> getContacts(Map<String, String> queryParams) throws VivialConnectException{
        return request(RequestMethod.GET, classURL(Contact.class), null, queryParams, ContactCollection.class).getContacts();
    }

     /**
     * Search for a {@link Contact} by its ID using the API.
     * <p>
     * If the {@link Contact} is not found, a VivialConnectException will be thrown.
     * 
     * @param contactId the id of the user to look up
     * 
     * @return the Contact that was found given the id
     * @throws VivialConnectException if there is an API-level error
     * 
     * @see #getContacts()
     * @see #getContacts(Map)
     */
    public static Contact getContactById(int contactId) throws VivialConnectException{
        return new Contact(request(RequestMethod.GET, classURLWithSuffix(Contact.class, String.valueOf(contactId)), null, null, Contact.class));
    }

    /**
     * Total number of contacts in the account specified. If there are none, this method will return <code>0</code>.
     * 
     * @return contact count
     * @throws VivialConnectException if there is an API-level error
     *
     */
    public static int count() throws VivialConnectException{
        return request(RequestMethod.GET, classURLWithSuffix(Contact.class, "count"), null, null, ResourceCount.class).getCount();
    }


    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
        ifParamValidAddToBuilder(jsonBodyBuilder, "id", getId());
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
        ifParamValidAddToBuilder(jsonBodyBuilder, "account_id", getAccountId());
    }

    public boolean isActive(){
        return active;
    }

    public void setActive(boolean active){
        this.active = active;
        jsonBodyBuilder.addParamPair("active", isActive());
    }

    public String getAddress1(){
        return address1;
    }

    public void setAddress1(String address1){
        this.address1 = address1;
        ifParamValidAddToBuilder(jsonBodyBuilder, "address1", getAddress1());
    }

    public String getAddress2(){
        return address2;
    }

    public void setAddress2(String address2){
        this.address2 = address2;
        ifParamValidAddToBuilder(jsonBodyBuilder, "address2", getAddress2());
    }

    public String getAddress3(){
        return address3;
    }

    public void setAddress3(String address3){
        this.address3 = address3;
        ifParamValidAddToBuilder(jsonBodyBuilder, "address3", getAddress3());
    }

    public String getCity(){
        return city;
    }

    public void setCity(String city){
        this.city = city;
        ifParamValidAddToBuilder(jsonBodyBuilder, "city", getCity());
    }

    public String getCompanyName(){
        return companyName;
    }

    public void setCompanyName(String companyName){
        this.companyName = companyName;
        ifParamValidAddToBuilder(jsonBodyBuilder, "company_name", getCompanyName());
    }

    public String getContactType(){
        return contactType;
    }

    public void setContactType(String contactType){
        this.contactType = contactType;
        ifParamValidAddToBuilder(jsonBodyBuilder, "contact_type", getContactType());
    }

    public String getCountry(){
        return country;
    }

    public void setCountry(String country){
        this.country = country;
        ifParamValidAddToBuilder(jsonBodyBuilder, "country", getCountry());
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
        ifParamValidAddToBuilder(jsonBodyBuilder, "email", getEmail());
    }

    public String getFax(){
        return fax;
    }

    public void setFax(String fax){
        this.fax = fax;
        ifParamValidAddToBuilder(jsonBodyBuilder, "fax", getFax());
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
        ifParamValidAddToBuilder(jsonBodyBuilder, "first_name", getFirstName());
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
        ifParamValidAddToBuilder(jsonBodyBuilder, "last_name", getLastName());
    }

    public String getMobilePhone(){
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone){
        this.mobilePhone = mobilePhone;
        ifParamValidAddToBuilder(jsonBodyBuilder, "mobile_phone", getMobilePhone());
    }

    public String getPostalCode(){
        return postalCode;
    }

    public void setPostalCode(String postalCode){
        this.postalCode = postalCode;
        ifParamValidAddToBuilder(jsonBodyBuilder, "postal_code", getPostalCode());
    }

    public String getState(){
        return state;
    }

    public void setState(String state){
        this.state = state;
        ifParamValidAddToBuilder(jsonBodyBuilder, "state", getState());
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
        ifParamValidAddToBuilder(jsonBodyBuilder, "title", getTitle());
    }

    public String getWorkPhone(){
        return workPhone;
    }

    public void setWorkPhone(String workPhone){
        this.workPhone = workPhone;
        ifParamValidAddToBuilder(jsonBodyBuilder, "work_phone", getWorkPhone());
    }
}