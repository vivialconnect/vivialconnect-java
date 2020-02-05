package net.vivialconnect.model.number;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import net.vivialconnect.model.ResourceCount;
import net.vivialconnect.model.VivialConnectResource;
import net.vivialconnect.model.account.Account;
import net.vivialconnect.model.error.NoContentException;
import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.format.JsonBodyBuilder;

import net.vivialconnect.model.error.BadRequestException;
import net.vivialconnect.model.error.ServerErrorException;
import net.vivialconnect.model.error.ApiRequestException;
import net.vivialconnect.model.error.ForbiddenAccessException;
import net.vivialconnect.model.error.UnauthorizedAccessException;

/**
 * Phone numbers are the basis for sending and receiving text messages to and from your Vivial Connect account.
 * In the course of sending text messages you must manage the phone number or set of phone numbers to use as the source and destination for your text messages.
 * <p>
 * The API supports US phone numbers only. When searching available numbers for a phone number to provision, you can search for
 * either Toll-Free or local (non-Toll-Free) numbers.
 */
@JsonRootName(value = "phone_number")
public class Number extends VivialConnectResource implements AssociatedNumber, AvailableNumber {

    private static final long serialVersionUID = -1224802858893763457L;

    private static final String AVAILABLE_US_LOCAL = "available/US/local";

    /**
     * Unique identifier of the phone number object
     */
    @JsonProperty
    private int id;

    /**
     * Creation date (UTC) of the phone number in ISO 8601 format
     */
    @JsonProperty("date_created")
    private Date dateCreated;

    /**
     * Last modification date (UTC) of phone number in ISO 8601 format
     */
    @JsonProperty("date_modified")
    private Date dateModified;

    /**
     * Unique identifier of the account or subaccount associated with the phone number
     */
    @JsonProperty("account_id")
    private int accountId;

    /**
     * Associated phone number as it is displayed to users. Default format: Friendly national format: (xxx) yyy-zzzz
     */
    @JsonProperty
    private String name;

    /**
     * Associated phone number in E.164 format (+country code +phone number). For US numbers, the format will be +1xxxyyyzzzz
     */
    @JsonProperty("phone_number")
    private String phoneNumber;

    /**
     * Type of associated phone number. Possible values: local (non-toll-free) or tollfree
     */
    @JsonProperty("phone_number_type")
    private String phoneNumberType;

    /**
     * URL to receive message status callback requests for messages sent via the API using this associated phone number
     */
    @JsonProperty("status_text_url")
    private String statusTextUrl;

    /**
     * URL for receiving SMS messages to the associated phone number. Max. length: 256 characters
     */
    @JsonProperty("incoming_text_url")
    private String incomingTextUrl;

    /**
     * HTTP method used for the incoming_text_url requests. Max. length: 8 characters. Possible values: GET or POST
     */
    @JsonProperty("incoming_text_method")
    private String incomingTextMethod;

    /**
     * URL for receiving SMS messages if incoming_text_url fails. Only valid if you provide a value for the incoming_text_url parameter. Max. length: 256 characters
     */
    @JsonProperty("incoming_text_fallback_url")
    private String incomingTextFallbackUrl;

    /**
     * HTTP method used for incoming_text_fallback_url requests. Max. length: 8 characters. Possible values: GET or POST
     */
    @JsonProperty("incoming_text_fallback_method")
    private String incomingTextFallbackMethod;

    /**
     * Number to which voice calls will be forwarded
     */
    @JsonProperty("voice_forwarding_number")
    private String voiceForwardingNumber;

    /**
     * Set of boolean flags indicating the following capabilities supported by the associated phone number
     */
    
    /*@JsonProperty
    private Capabilities capabilities;*/

    /**
     * City where the available phone number is located
     */
    @JsonProperty
    private String city;

    /**
     * Two-letter US state abbreviation where the available phone number is located
     */
    @JsonProperty
    private String region;

    /**
     * Local address and transport area (LATA) where the available phone number is located
     */
    @JsonProperty
    private String lata;

    /**
     * LATA rate center where the available phone number is located. Usually the same as city
     */
    @JsonProperty("rate_center")
    private String rateCenter;

    @JsonProperty
    private boolean active;

    @JsonProperty("connector_id")
    private int connectorId;

    /**
     * Tags associated to this number instance
     */
    @JsonProperty("tags")
    private Map<String, String> tags;

    static {
        classesWithoutRootValue.add(NumberCollection.class);
        classesWithoutRootValue.add(TagCollection.class);
        classesWithoutRootValue.add(TaggedNumberCollection.class);
    }

    /**
     * Updates this number by sending to the server the properties that were changed via
     * this class's set methods.
     * <p>
     * For more details on which properties can be updated, refer to the VivialConnect API documentation's
     * <a href="https://dashboard.vivialconnect.net/docs/api/numbers.html#put--api-v1.0-accounts-(int-account_id)-numbers-(id).json">number section.</a>
     * <p>
     * If the number you're trying to update does not exist, a {@link VivialConnectException}
     * holding a 404 response code will be thrown.
     *
     * @return this instance of {@link AssociatedNumber} with the updated properties
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    @Override
    public AssociatedNumber update() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        AssociatedNumber number = request(RequestMethod.PUT, classURLWithSuffix(Number.class, String.valueOf(getId())),
                buildJsonBodyForUpdate(), null, Number.class);
        updateObjectState(number);

        return this;
    }


    private String buildJsonBodyForUpdate() {
        JsonBodyBuilder builder = JsonBodyBuilder.withCustomClassName("phone_number");
        fillOptionalFieldsForUpdate(builder);

        return builder.build();
    }


    private void fillOptionalFieldsForUpdate(JsonBodyBuilder builder) {
        ifParamValidAddToBuilder(builder, "id", getId());
        ifParamValidAddToBuilder(builder, "connector_id", getConnectorId());
        ifParamValidAddToBuilder(builder, "incoming_text_url", getIncomingTextUrl());
        ifParamValidAddToBuilder(builder, "incoming_text_method", getIncomingTextMethod());
        ifParamValidAddToBuilder(builder, "incoming_text_fallback_url", getIncomingTextFallbackUrl());
        ifParamValidAddToBuilder(builder, "incoming_text_fallback_method", getIncomingTextFallbackMethod());
        ifParamValidAddToBuilder(builder, "voice_forwarding_number", getVoiceForwardingNumber());
    }


    private void updateObjectState(AssociatedNumber number) {
        this.id = number.getId();
        this.dateCreated = number.getDateCreated();
        this.dateModified = number.getDateModified();
        this.accountId = number.getAccountId();
        this.phoneNumber = number.getPhoneNumber();
        this.phoneNumberType = number.getPhoneNumberType();
        this.statusTextUrl = number.getStatusTextUrl();
        this.incomingTextUrl = number.getIncomingTextUrl();
        this.incomingTextMethod = number.getIncomingTextMethod();
        this.incomingTextFallbackUrl = number.getIncomingTextFallbackUrl();
        this.incomingTextFallbackMethod = number.getIncomingTextFallbackMethod();
        this.voiceForwardingNumber = number.getVoiceForwardingNumber();
        /* this.capabilities = number.getCapabilities(); */
        this.city = number.getCity();
        this.region = number.getRegion();
        this.lata = number.getLata();
        this.rateCenter = number.getRateCenter();
        this.active = number.isActive();
        this.connectorId = number.getConnectorId();
    }

    /**
     * Updates information about a phone number associated with your account
     *
     * @return associated number
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    @Override
    public AssociatedNumber updateLocalNumber() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        ensureNumberIsLocal();

        AssociatedNumber number = request(RequestMethod.PUT, classURLWithSuffix(Number.class, String.format("local/%d", getId())),
                buildJsonBodyForUpdate(), null, Number.class);
        updateObjectState(number);

        return this;
    }


    private void ensureNumberIsLocal() {
        if (!"local".equals(phoneNumberType)) {
            throw new UnsupportedOperationException("Number is not local");
        }
    }

    /**
     * Deletes this number from the database, dissociating it from the account.
     * <p>
     * If the number you're trying to delete does not exist, a {@link VivialConnectException}
     * holding a 404 response code will be thrown.
     *
     * @return a boolean value, indicating whether the number was deleted or not
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    @Override
    public boolean delete() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        try {
            request(RequestMethod.DELETE, classURLWithSuffix(Number.class, String.valueOf(getId())), null, null, String.class);
        } catch (NoContentException e) {
            return true;
        }

        return false;
    }

    /**
     * Deletes this local number from the database, dissociating it from the account.
     * <p>
     * If the local number you're trying to delete does not exist, a {@link VivialConnectException}
     * holding a 404 response code will be thrown.
     *
     * @return a boolean value, indicating whether the local number was deleted or not
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    @Override
    public boolean deleteLocalNumber() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        ensureNumberIsLocal();

        try {
            request(RequestMethod.DELETE, classURLWithSuffix(Number.class, String.format("local/%d", getId())), null, null, String.class);
        } catch (NoContentException e) {
            return true;
        }

        return false;
    }

    /**
     * Purchases a new number using the properties: phone number and phone number type
     *
     * @return the new Number purchased
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    public AssociatedNumber buy() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        JsonBodyBuilder builder = JsonBodyBuilder.withCustomClassName("phone_number")
                .addParamPair("phone_number", getPhoneNumber())
                .addParamPair("phone_number_type", getPhoneNumberType());
        fillOptionalFieldsForBuy(builder);

        return request(RequestMethod.POST, classURL(Number.class), builder.build(), null, Number.class);
    }


    private void fillOptionalFieldsForBuy(JsonBodyBuilder builder) {
        ifParamValidAddToBuilder(builder, "name", getName());
        ifParamValidAddToBuilder(builder, "status_text_url", getStatusTextUrl());
        ifParamValidAddToBuilder(builder, "connector_id", getConnectorId());
        ifParamValidAddToBuilder(builder, "incoming_text_url", getIncomingTextUrl());
        ifParamValidAddToBuilder(builder, "incoming_text_method", getIncomingTextMethod());
        ifParamValidAddToBuilder(builder, "incoming_text_fallback_url", getIncomingTextFallbackUrl());
        ifParamValidAddToBuilder(builder, "incoming_text_fallback_method", getIncomingTextFallbackMethod());
    }

    //TODO: Deprecate this

    /**
     * Purchases the specified phone number in this area code.
     *
     * @param phoneNumber    Phone number you want to purchase in E.164 format (+country code +phone number). For US numbers, the format will be +1xxxyyyzzzz.
     *                       If you specify this parameter, the area_code parameter will be ignored.
     * @param areaCode       Three-digit US area code you want to specify for the phone number you want to purchase. The API will assign a random number within
     *                       the area code. You must specify this parameter if phone_number is not specified.
     * @param optionalParams a map of {@link String } and {@link Object } key-value pairs used to filter results, possible values are:
     *                       <p>
     *                       <code>name</code> – New phone number as it is displayed to users. Default format: Friendly national format: (xxx) yyy-zzzz.
     *                       <p>
     *                       <code>status_text_url</code> – URL to receive message status callback requests for messages sent via the API using this associated phone number.
     *                       Max. length: 256 characters.
     *                       <p>
     *                       <code>connector_id</code> – Unique identifier of the connector this message was sent over, if any.
     *                       <p>
     *                       <code>incoming_text_url</code> – URL for receiving SMS messages to the associated phone number. Max. length: 256 characters.
     *                       <p>
     *                       <code>incoming_text_method</code> – HTTP method used for the incoming_text_url requests. Max. length: 8 characters. Possible values: GET or POST. Default value: POST.
     *                       <p>
     *                       <code>incoming_text_fallback_url</code> – URL for receiving SMS messages if incoming_text_url fails. Only valid if you provide a value for the incoming_text_url parameter.
     *                       Max. length: 256 characters.
     *                       <p>
     *                       <code>incoming_text_fallback_method</code> – HTTP method used for incoming_text_url_fallback requests. Max. length: 8 characters. Possible values: GET or POST. Default value: POST.
     * @return associated number
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    public static AssociatedNumber buyLocalNumber(String phoneNumber, String areaCode, Map<String, Object> optionalParams) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        JsonBodyBuilder builder = JsonBodyBuilder.withCustomClassName("phone_number");
        if (optionalParams != null) {
            builder = builder.addParams(optionalParams);
        }
        ifParamValidAddToBuilder(builder, "phone_number", phoneNumber);
        ifParamValidAddToBuilder(builder, "area_code", areaCode);

        return request(RequestMethod.POST, classURLWithSuffix(Number.class, "local"), builder.build(), null, Number.class);
    }

    //TODO: Code revision for the random area code

    /**
     * Purchases the specified phone number in this area code with a phone number type.
     *
     * @param phoneNumber     Phone number you want to purchase in E.164 format (+country code +phone number). For US numbers, the format will be +1xxxyyyzzzz.
     *                        If you specify this parameter, the area_code parameter will be ignored.
     * @param areaCode        Three-digit US area code you want to specify for the phone number you want to purchase. The API will assign a random number within
     *                        the area code. You must specify this parameter if phone_number is not specified.
     * @param phoneNumberType {@link String} with the phone number type i.e local
     * @param optionalParams  a map of {@link String } anf {@link Object } key-value pairs used to filter results, possible values are:
     *                        <p>
     *                        <code>name</code> – New phone number as it is displayed to users. Default format: Friendly national format: (xxx) yyy-zzzz.
     *                        <p>
     *                        <code>status_text_url</code> – URL to receive message status callback requests for messages sent via the API using this associated phone number.
     *                        Max. length: 256 characters.
     *                        <p>
     *                        <code>connector_id</code> – Unique identifier of the connector this message was sent over, if any.
     *                        <p>
     *                        <code>incoming_text_url</code> – URL for receiving SMS messages to the associated phone number. Max. length: 256 characters.
     *                        <p>
     *                        <code>incoming_text_method</code> – HTTP method used for the incoming_text_url requests. Max. length: 8 characters. Possible values: GET or POST. Default value: POST.
     *                        <p>
     *                        <code>incoming_text_fallback_url</code> – URL for receiving SMS messages if incoming_text_url fails. Only valid if you provide a value for the incoming_text_url parameter.
     *                        Max. length: 256 characters.
     *                        <p>
     *                        <code>incoming_text_fallback_method</code> – HTTP method used for incoming_text_url_fallback requests. Max. length: 8 characters. Possible values: GET or POST. Default value: POST.
     * @return associated number
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    public static AssociatedNumber buy(String phoneNumber, String areaCode, String phoneNumberType, Map<String, Object> optionalParams) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        JsonBodyBuilder builder = JsonBodyBuilder.withCustomClassName("phone_number");
        if (optionalParams != null) {
            builder = builder.addParams(optionalParams);
        }
        ifParamValidAddToBuilder(builder, "phone_number", phoneNumber);
        ifParamValidAddToBuilder(builder, "area_code", areaCode);
        ifParamValidAddToBuilder(builder, "phone_number_type", phoneNumberType);

        return request(RequestMethod.POST, classURL(Number.class), builder.build(), null, Number.class);
    }

    /**
     * Gets all phone numbers associated with your account using the API.
     * <p>
     * If no numbers were found for this {@link Account},the method will return an empty {@link List}
     *
     * @return a list of associated number
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     * @see #getAssociatedNumbers(Map)
     */
    public static List<AssociatedNumber> getAssociatedNumbers() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        return getAssociatedNumbers(null);
    }

    /**
     * Search and filter every associated number for this Account using the API.
     * <p>
     * If no {@link AssociatedNumber} were found for this {@link Account}, the method will return an empty {@link List}
     *
     * @param queryParams a map of {@link String } key-value pairs used to filter results, possible values are:
     *                    <p>
     *                    <code>page</code> – Page number within the returned list of associated phone numbers. Default value: 1.
     *                    <p>
     *                    <code>limit</code> – Number of results to return per page. Default value: 50. Maximum value: 150.
     *                    <p>
     *                    <code>name</code> – Filters the results to include only phone numbers exactly matching the specified name.
     *                    <p>
     *                    <code>contains</code> – Filters the results to include only phone numbers that match a number pattern you specify.
     *                    The pattern can include digits and the following wildcard characters:
     *                    <p>
     *                    ? : matches any single digit
     *                    <br>
     *                    * : matches zero or more digits
     * @return a list of associated number
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     * @see #getAssociatedNumbers()
     */
    public static List<AssociatedNumber> getAssociatedNumbers(Map<String, String> queryParams) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        return request(RequestMethod.GET, classURL(Number.class), null, queryParams, NumberCollection.class).getAssociatedNumbers();
    }

    /**
     * Search for available phone numbers in a specific region using the API.
     * <p>
     * If no numbers were found for this {@link Account}, the method will return an empty {@link List}
     *
     * @param region 2-letter {@link String } region (US state).
     * @return a list of available number
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     * @see #findAvailableNumbersInRegion(String, Map)
     */
    public static List<AvailableNumber> findAvailableNumbersInRegion(String region) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        return findAvailableNumbersInRegion(region, null);
    }

    /**
     * Search and filter available phone numbers in a specific region using the API.
     * <p>
     * If no numbers were found for this {@link Account}, the method will return an empty {@link List}
     *
     * @param region      2-letter {@link String } region (US state).
     * @param queryParams a map of {@link String } key-value pairs used to filter results, possible values are:
     *                    <p>
     *                    <code>limit</code> – Number of results to return per page. Default value: 50. Maximum value: 150.
     *                    <p>
     *                    <code>contains</code> – Filters the results to include only phone numbers that match a number pattern you specify.
     *                    The pattern can include letters, digits, and the following wildcard characters:
     *                    <p>
     *                    ? : matches any single digit
     *                    <br>
     *                    : matches zero or more digits
     *                    <p>
     *                    <code>in_postal_code</code> – Filters the results to include only phone numbers in a specified 5-digit postal code.
     *                    <p>
     *                    <code>area_code</code> – Filters the results to include only phone numbers by US area code.
     *                    <p>
     *                    <code>in_city</code> – Filters the results to include only phone numbers in a specified city.
     *                    <p>
     *                    <code>local_number</code> – Filters the results to include only phone numbers that match the first three or more digits you
     *                    specify to immediately follow the area code. To use this parameter, you must also specify an area_code.
     * @return a list of available number
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    public static List<AvailableNumber> findAvailableNumbersInRegion(String region, Map<String, String> queryParams) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        return request(RequestMethod.GET, classURLWithSuffix(Number.class, AVAILABLE_US_LOCAL), null, addQueryParam("in_region", region, queryParams), NumberCollection.class).getAvailableNumbers();
    }

    /**
     * Search for available phone numbers in a specific area code using the API.
     * <p>
     * If no numbers were found for this {@link Account}, the method will return an empty {@link List}
     *
     * @param areaCode {@link String } representing a US area code.
     * @return a list of available number
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    public static List<AvailableNumber> findAvailableNumbersByAreaCode(String areaCode) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        return findAvailableNumbersByAreaCode(areaCode, null);
    }

    /**
     * Search and filter available phone numbers in a specific area code using the API.
     * <p>
     * If no numbers were found for this {@link Account}, the method will return an empty {@link List}
     *
     * @param areaCode    {@link String } representing a US area code.
     * @param queryParams a map of {@link String } key-value pairs used to filter results, possible values are:
     *                    <p>
     *                    <code>limit</code> – Number of results to return per page. Default value: 50. Maximum value: 150.
     *                    <p>
     *                    <code>contains</code> – Filters the results to include only phone numbers that match a number pattern you specify.
     *                    The pattern can include letters, digits, and the following wildcard characters:
     *                    <p>
     *                    ? : matches any single digit
     *                    <br>
     *                    : matches zero or more digits
     *                    <p>
     *                    <code>in_postal_code</code> – Filters the results to include only phone numbers in a specified 5-digit postal code.
     *                    <p>
     *                    <code>in_region</code> – Filters the results include only phone numbers in a specified 2-letter region (US state).
     *                    <p>
     *                    <code>in_city</code> – Filters the results to include only phone numbers in a specified city.
     *                    <p>
     *                    <code>local_number</code> – Filters the results to include only phone numbers that match the first three or more digits you
     *                    specify to immediately follow the area code. To use this parameter, you must also specify an area_code.
     * @return a list of available number
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    public static List<AvailableNumber> findAvailableNumbersByAreaCode(String areaCode, Map<String, String> queryParams) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        return request(RequestMethod.GET, classURLWithSuffix(Number.class, AVAILABLE_US_LOCAL), null, addQueryParam("area_code", areaCode, queryParams), NumberCollection.class).getAvailableNumbers();
    }

    /**
     * Search for available phone numbers in a specific postal code using the API.
     * <p>
     * If no numbers were found for this {@link Account}, the method will return an empty {@link List}
     *
     * @param postalCode 5-digit {@link String } postal code.
     * @return a list of available number
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    public static List<AvailableNumber> findAvailableNumbersByPostalCode(String postalCode) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        return findAvailableNumbersByPostalCode(postalCode, null);
    }

    /**
     * Search and filter available phone numbers in a specific postal code using the API.
     * <p>
     * If no numbers were found for this {@link Account}, the method will return an empty {@link List}
     *
     * @param postalCode  5-digit {@link String } postal code.
     * @param queryParams a map of {@link String } key-value pairs used to filter results, possible values are:
     *                    <p>
     *                    <code>limit</code> – Number of results to return per page. Default value: 50. Maximum value: 150.
     *                    <p>
     *                    <code>contains</code> – Filters the results to include only phone numbers that match a number pattern you specify.
     *                    The pattern can include letters, digits, and the following wildcard characters:
     *                    <p>
     *                    ? : matches any single digit
     *                    <br>
     *                    : matches zero or more digits
     *                    <p>
     *                    <code>area_code</code> – Filters the results to include only phone numbers by US area code.
     *                    <p>
     *                    <code>in_region</code> – Filters the results include only phone numbers in a specified 2-letter region (US state).
     *                    <p>
     *                    <code>in_city</code> – Filters the results to include only phone numbers in a specified city.
     *                    <p>
     *                    <code>local_number</code> – Filters the results to include only phone numbers that match the first three or more digits you
     *                    specify to immediately follow the area code. To use this parameter, you must also specify an area_code.
     * @return a list of available number
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    public static List<AvailableNumber> findAvailableNumbersByPostalCode(String postalCode, Map<String, String> queryParams) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        return request(RequestMethod.GET, classURLWithSuffix(Number.class, AVAILABLE_US_LOCAL), null, addQueryParam("in_postal_code", postalCode, queryParams), NumberCollection.class).getAvailableNumbers();
    }

    /**
     * Gets all the numbers associated with this account. If there are none, this method will return an empty { @link List }
     *
     * @return a list of associated number
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     * @see #getLocalAssociatedNumbers(Map)
     */
    public static List<AssociatedNumber> getLocalAssociatedNumbers() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        return getLocalAssociatedNumbers(null);
    }

    /**
     * Lists and filters numbers associated with the current account. If there are none, the method will return an empty {@link List}
     *
     * @param queryParams a map of {@link String } key-value pairs used to filter results, possible values are:
     *                    <p>
     *                    <code>page</code> – Page number within the returned list of associated phone numbers. Default value: 1.
     *                    <p>
     *                    <code>limit</code> – Number of results to return per page. Default value: 50. Maximum value: 150.
     *                    <p>
     *                    <code>name</code> – Filters the results to include only phone numbers exactly matching the specified name.
     *                    <p>
     *                    <code>contains</code> – Filters the results to include only phone numbers that match a number pattern you specify.
     *                    The pattern can include digits and the following wildcard characters:
     *                    <p>
     *                    ? : matches any single digit
     *                    <br>
     *                    * : matches zero or more digits
     * @return a list of associated number
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    public static List<AssociatedNumber> getLocalAssociatedNumbers(Map<String, String> queryParams) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        return request(RequestMethod.GET, classURLWithSuffix(Number.class, "local"), null, queryParams, NumberCollection.class).getAssociatedNumbers();
    }

    /**
     * Total number of phone numbers in the account. If there are none, this method will return <code>0</code>.
     *
     * @return number count
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    public static int count() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        return request(RequestMethod.GET, classURLWithSuffix(Number.class, "count"), null, null, ResourceCount.class).getCount();
    }

    /**
     * Total number of local numbers in the account. If there are none, this method will return <code>0</code>.
     *
     * @return local number count
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    public static int countLocal() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        return request(RequestMethod.GET, classURLWithSuffix(Number.class, "local/count"), null, null, ResourceCount.class).getCount();
    }

    /**
     * Retrieves a single associated number given an id.
     *
     * @param numberId the id of the associated number to look up
     * @return the AssociatedNumber found, or null if not found
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    public static AssociatedNumber getNumberById(int numberId) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        return request(RequestMethod.GET, classURLWithSuffix(Number.class, String.valueOf(numberId)), null, null, Number.class);
    }

    /**
     * Retrieves a single local number given an id.
     *
     * @param numberId the id of the local number to look up
     * @return the AssociatedNumber found, or null if not found
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    public static AssociatedNumber getLocalNumberById(int numberId) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        return request(RequestMethod.GET, classURLWithSuffix(Number.class, String.format("local/%d", numberId)), null, null, Number.class);
    }

    /**
     * Gets information about the device type and carrier that is associated with a specific phone number
     *
     * @return number info, or null if not found
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    @Override
    public NumberInfo lookup() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("phone_number", getRawPhoneNumber());

        return request(RequestMethod.GET, classURLWithSuffix(Number.class, "lookup"), null, queryParams, NumberInfo.class);
    }


    private String getRawPhoneNumber() {
        /* Removes the leading '+' character from the phone number */
        return getPhoneNumber().substring(1);
    }

    /**
     * Unique identifier of the phone number object.
     *
     * @return unique object ID value
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Set unique identifier of the phone number object.
     *
     * @param id unique identifier value
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Creation date of the phone number
     *
     * @return creation date value
     */
    @Override
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * Set the creation date of the phone number
     *
     * @param dateCreated creation date value
     */
    @Override
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Last modification date (UTC) of the phone number
     *
     * @return last modification date value
     */
    @Override
    public Date getDateModified() {
        return dateModified;
    }

    /**
     * Set the phone number modification date
     *
     * @param dateModified modified date value
     */
    @Override
    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    /**
     * Unique identifier of the account associated with the phone number
     *
     * @return account ID value
     */
    @Override
    public int getAccountId() {
        return accountId;
    }

    /**
     * Set the account ID value for the phone number
     *
     * @param accountId account ID value
     */
    @Override
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    /**
     * Associated phone number as it is displayed to users.
     *
     * @return phone number's name value
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Set the name of the phone number
     *
     * @param name phone number's name value
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Associated phone number in E.164 format (+country code +phone number). For US numbers, the format will be +1xxxyyyzzzz.
     *
     * @return literal phone number value
     */
    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Set associated phone number in E.164 format
     *
     * @param phoneNumber phone number value
     */
    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Type of associated phone number. Possible values: local or tollfree.
     *
     * @return phone number type value
     */
    @Override
    public String getPhoneNumberType() {
        return phoneNumberType;
    }

    /**
     * Set phone number type value
     *
     * @param phoneNumberType phone number type value
     */
    @Override
    public void setPhoneNumberType(String phoneNumberType) {
        this.phoneNumberType = phoneNumberType;
    }

    /**
     * URL to receive message status callback requests for messages sent via the API using this associated phone number.
     *
     * @return status text URL value
     */
    @Override
    public String getStatusTextUrl() {
        return statusTextUrl;
    }

    /**
     * Set status text URL value
     *
     * @param statusTextUrl Status Text URL value
     */
    @Override
    public void setStatusTextUrl(String statusTextUrl) {
        this.statusTextUrl = statusTextUrl;
    }

    /**
     * URL for receiving SMS messages to the associated phone number.
     *
     * @return incoming text URL value
     */
    @Override
    public String getIncomingTextUrl() {
        return incomingTextUrl;
    }

    /**
     * Set incoming text url value
     *
     * @param incomingTextUrl incoming text URL value
     */
    @Override
    public void setIncomingTextUrl(String incomingTextUrl) {
        this.incomingTextUrl = incomingTextUrl;
    }

    /**
     * HTTP method used for the incoming_text_url requests
     *
     * @return incoming text ulr value
     */
    @Override
    public String getIncomingTextMethod() {
        return incomingTextMethod;
    }

    /**
     * Set incoming text URL method
     *
     * @param incomingTextMethod incoming text URL method.
     */
    @Override
    public void setIncomingTextMethod(String incomingTextMethod) {
        this.incomingTextMethod = incomingTextMethod;
    }

    /**
     * URL for receiving SMS messages if incoming_text_url fails.
     * Only valid if you provide a value for the incoming_text_url parameter.
     *
     * @return incoming text fallback URL
     */
    @Override
    public String getIncomingTextFallbackUrl() {
        return incomingTextFallbackUrl;
    }

    /**
     * Set the incoming text fallback URL value
     *
     * @param incomingTextFallbackUrl incoming text fallback URL value
     */
    @Override
    public void setIncomingTextFallbackUrl(String incomingTextFallbackUrl) {
        this.incomingTextFallbackUrl = incomingTextFallbackUrl;
    }

    /**
     * HTTP method used for incoming_text_fallback_url requests.
     *
     * @return incoming text fallback method value
     */
    @Override
    public String getIncomingTextFallbackMethod() {
        return incomingTextFallbackMethod;
    }

    /**
     * Set incoming text fallback method value
     *
     * @param incomingTextFallbackMethod incoming text fallback method value
     */
    @Override
    public void setIncomingTextFallbackMethod(String incomingTextFallbackMethod) {
        this.incomingTextFallbackMethod = incomingTextFallbackMethod;
    }

    /**
     * Number to which voice calls will be forwarded.
     *
     * @return voice forward value
     */
    @Override
    public String getVoiceForwardingNumber() {
        return voiceForwardingNumber;
    }

    /**
     * Set  voice forward value
     *
     * @param voiceForwardingNumber voice forwarding value
     */
    @Override
    public void setVoiceForwardingNumber(String voiceForwardingNumber) {
        this.voiceForwardingNumber = voiceForwardingNumber;
    }


    /* @Override
    public Capabilities getCapabilities(){
        return capabilities;
    }


    @Override
    public void setCapabilities(Capabilities capabilities){
        this.capabilities = capabilities;
    } */

    /**
     * City where the phone number is located.
     *
     * @return city name value
     */
    @Override
    public String getCity() {
        return city;
    }

    /**
     * Set city name value
     *
     * @param city city value
     */
    @Override
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Two-letter US state abbreviation where the phone number is located.
     *
     * @return region value
     */
    @Override
    public String getRegion() {
        return region;
    }

    /**
     * Set the phone number region value
     *
     * @param region region value
     */
    @Override
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Local address and transport area (LATA) where the available phone number is located.
     *
     * @return phone number's LATA value
     */
    @Override
    public String getLata() {
        return lata;
    }

    /**
     * Set LATA value of the phone number
     *
     * @param lata LATA value
     */
    @Override
    public void setLata(String lata) {
        this.lata = lata;
    }

    /**
     * LATA rate center where the phone number is located.
     *
     * @return Rate Center value
     */
    @Override
    public String getRateCenter() {
        return rateCenter;
    }

    /**
     * Set the phone number's rate center
     *
     * @param rateCenter rate center value
     */
    @Override
    public void setRateCenter(String rateCenter) {
        this.rateCenter = rateCenter;
    }

    /**
     * Determine if the phone number value is enabled or not.
     *
     * @return phone number active value
     */
    @Override
    public boolean isActive() {
        return active;
    }

    /**
     * Set the phone number active value
     *
     * @param active active value
     */
    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Connector ID to witch this phone number belongs
     *
     * @return Connector ID value
     */
    @Override
    public int getConnectorId() {
        return connectorId;
    }

    /**
     * Set connector ID value for the phone number
     *
     * @param connectorId connector ID value
     */
    @Override
    public void setConnectorId(int connectorId) {
        this.connectorId = connectorId;
    }

    /**
     * User-defined tags as key-value pair
     *
     * @return phone number tags value
     */
    public Map<String, String> getTags() {
        return tags;
    }

    /**
     * Set tags for the phone number
     *
     * @param tags phone number tags value
     */
    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    /**
     * Update the tags to the API
     *
     * @param tags String map with  key-pair values with the tag names and values
     * @return Updated tags
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    @Override
    public TagCollection updateTags(Map<String, String> tags) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {

        String requestPayload = JsonBodyBuilder.withCustomClassName("tags").addTypedParams(tags).build();

        String idValue = String.valueOf(getId());
        TagCollection tagsResponse = request(RequestMethod.PUT, classURLWithResourceSuffix(Number.class, idValue, "tags"),
                requestPayload, null, TagCollection.class);

        this.tags = tagsResponse.getTags();

        return tagsResponse;
    }

    /**
     * Get all tagged numbers associated to the user's account.
     *
     * @return Collection of tagged numbers
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    public static TaggedNumberCollection getTaggedNumbers() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        return getTaggedNumbers(null);
    }

    /**
     * Get all tagged numbers associated to the user's account.
     *
     * @param requestParams a map of {@link String } key-value pairs used to filter results, possible values are:
     *                      - page – Page number to return. Default value: 1.
     *                      - limit – Number of results to return per page. Default value: 50. Maximum value: 150.
     *                      - contains – Comma-separated list of key:value pairs. Filters results to included only phone numbers tagged with these pairs.
     *                      - notcontains – Comma-separated list of key:value pairs. Filters results to exclude phone numbers tagged with these pairs.
     * @return Collection of tagged numbers
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    public static TaggedNumberCollection getTaggedNumbers(Map<String, String> requestParams) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {

        TaggedNumberCollection tagResponse = request(RequestMethod.GET, classURLWithSuffix(Number.class, "tags"), null,
                requestParams, TaggedNumberCollection.class);

        return tagResponse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Number)) return false;

        Number number = (Number) o;

        if (id != number.id) return false;
        if (accountId != number.accountId) return false;
        if (active != number.active) return false;
        if (connectorId != number.connectorId) return false;
        if (dateCreated != null ? !dateCreated.equals(number.dateCreated) : number.dateCreated != null) return false;
        if (dateModified != null ? !dateModified.equals(number.dateModified) : number.dateModified != null)
            return false;
        if (name != null ? !name.equals(number.name) : number.name != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(number.phoneNumber) : number.phoneNumber != null) return false;
        if (phoneNumberType != null ? !phoneNumberType.equals(number.phoneNumberType) : number.phoneNumberType != null)
            return false;
        if (statusTextUrl != null ? !statusTextUrl.equals(number.statusTextUrl) : number.statusTextUrl != null)
            return false;
        if (incomingTextUrl != null ? !incomingTextUrl.equals(number.incomingTextUrl) : number.incomingTextUrl != null)
            return false;
        if (incomingTextMethod != null ? !incomingTextMethod.equals(number.incomingTextMethod) : number.incomingTextMethod != null)
            return false;
        if (incomingTextFallbackUrl != null ? !incomingTextFallbackUrl.equals(number.incomingTextFallbackUrl) : number.incomingTextFallbackUrl != null)
            return false;
        if (incomingTextFallbackMethod != null ? !incomingTextFallbackMethod.equals(number.incomingTextFallbackMethod) : number.incomingTextFallbackMethod != null)
            return false;
        if (voiceForwardingNumber != null ? !voiceForwardingNumber.equals(number.voiceForwardingNumber) : number.voiceForwardingNumber != null)
            return false;
        if (city != null ? !city.equals(number.city) : number.city != null) return false;
        if (region != null ? !region.equals(number.region) : number.region != null) return false;
        if (lata != null ? !lata.equals(number.lata) : number.lata != null) return false;
        if (rateCenter != null ? !rateCenter.equals(number.rateCenter) : number.rateCenter != null) return false;
        return tags != null ? tags.equals(number.tags) : number.tags == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        result = 31 * result + (dateModified != null ? dateModified.hashCode() : 0);
        result = 31 * result + accountId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (phoneNumberType != null ? phoneNumberType.hashCode() : 0);
        result = 31 * result + (statusTextUrl != null ? statusTextUrl.hashCode() : 0);
        result = 31 * result + (incomingTextUrl != null ? incomingTextUrl.hashCode() : 0);
        result = 31 * result + (incomingTextMethod != null ? incomingTextMethod.hashCode() : 0);
        result = 31 * result + (incomingTextFallbackUrl != null ? incomingTextFallbackUrl.hashCode() : 0);
        result = 31 * result + (incomingTextFallbackMethod != null ? incomingTextFallbackMethod.hashCode() : 0);
        result = 31 * result + (voiceForwardingNumber != null ? voiceForwardingNumber.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (lata != null ? lata.hashCode() : 0);
        result = 31 * result + (rateCenter != null ? rateCenter.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + connectorId;
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }

    /**
     * Fetch and refresh the tags for an instance of Number. This method represents the endpoint:
     * <pre>GET /api/v1.0/accounts/(int: account_id)/numbers/(number_id)/tags.json</pre> in the API.
     *
     * @return a TagCollection object with the tags of a Number instance.
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    public TagCollection fetchTags() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        TagCollection tags = request(RequestMethod.GET, classURLWithResourceSuffix(Number.class, String.valueOf(this.id), "tags"),
                null, null, TagCollection.class);
        this.tags = tags.getTags();

        return tags;
    }

    /**
     * Delete individual key/value pairs from phone number tags.
     * <p>
     * Values are ignored if:
     *
     * <ul>
     *     <li>Existing tag keys included in payload will be removed from tags.</li>
     *     <li>Existing tag keys NOT included in the payload will be left unchanged.</li>
     * </ul>
     *
     * @param tags tags to delete.
     * @return a TagCollection object with the tags remaining.
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params or/and payload  are not valid
     * @throws UnauthorizedAccessException if the any of the auth properties: Account ID, API Key and API secret, are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     * */
    @Override
    public TagCollection deleteTags(Map<String, String> tags) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {

        String requestPayload = JsonBodyBuilder.withCustomClassName("tags").addTypedParams(tags).build();

        TagCollection tagsLeft = request(RequestMethod.DELETE, classURLWithResourceSuffix(Number.class, String.valueOf(this.id), "tags"),
                requestPayload, null, TagCollection.class);

        this.tags = tagsLeft.getTags();

        return tagsLeft;
    }


}