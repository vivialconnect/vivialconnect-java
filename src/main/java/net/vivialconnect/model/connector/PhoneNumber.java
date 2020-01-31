package net.vivialconnect.model.connector;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import net.vivialconnect.model.ResourceCount;
import net.vivialconnect.model.VivialConnectResource;
import net.vivialconnect.model.error.VivialConnectException;

import java.util.HashMap;
import java.util.Map;

/**
 * In the API, the phone_numbers resource allows you to associate or disassociate phone numbers from a Connector.
 * When creating or editing a phone number association, you must provide a phone_number or phone_number_id for a phone number you own.
 * Currently, a phone number can only be associated with a single Connector.
 * <p>
 * This class represents a phone number entity that can operated over a connector.
 */
@JsonRootName("phone_number")
public class PhoneNumber extends VivialConnectResource {

    private static final long serialVersionUID = -621133784366767246L;

    /**
     * A string containing the phone number
     */
    @JsonProperty("phone_number")
    private String phoneNumber;

    /**
     * An integer representing the id of the PhoneNumber resource associated
     */
    @JsonProperty("phone_number_id")
    private int phoneNumberId;

    public PhoneNumber() {

    }

    /**
     * Convenient constructor for create a connector's phone number
     *
     * @param phoneNumberId phone number ID
     * @param phoneNumber   phone number value
     */
    public PhoneNumber(int phoneNumberId, String phoneNumber) {
        this.phoneNumberId = phoneNumberId;
        this.phoneNumber = phoneNumber;
    }


    /**
     * @param connectorId connector ID
     * @see PhoneNumber#getPhoneNumbers(int, int)
     * @throws VivialConnectException if the connector does not exist or an error occurs at API-level
     */
    public static ConnectorWithPhoneNumbers getPhoneNumbers(int connectorId) throws VivialConnectException {
        return getPhoneNumbers(connectorId, 1);
    }

    /**
     * List of phone numbers associated to a connector
     *
     * @param connectorId connector ID value
     * @param page        page number value
     * @return an instance of connector with a list of its phone numbers
     * @throws VivialConnectException if the connector does not exist or an error occurs at API-level
     */
    public static ConnectorWithPhoneNumbers getPhoneNumbers(int connectorId, int page) throws VivialConnectException {

        Map<String, String> pageParam = new HashMap<String, String>();
        pageParam.put("page", String.valueOf(page));

        ConnectorWithPhoneNumbers connector = request(RequestMethod.GET, classURLWithSuffix(Connector.class, String.format("%d/phone_numbers", connectorId)), null, pageParam,
                ConnectorPaginatedPhoneNumbers.class).getConnector();

        return connector;
    }

    /**
     * Count of phone numbers associated to a connector
     *
     * @param connectorId connector ID
     * @return count of phone numbers associated to the connector
     * @throws VivialConnectException if the connector does not exist or an error occurs at API-level
     */
    public static int count(int connectorId) throws VivialConnectException {
        return request(RequestMethod.GET, classURLWithSuffix(Connector.class, String.format("%d/phone_numbers/count", connectorId)), null, null, ResourceCount.class).getCount();
    }

    /**
     * Literal phone number value
     *
     * @return phone number value
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Set phone number
     *
     * @param phoneNumber phone number value
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Phone number ID
     *
     * @return phone number ID value
     */
    public int getPhoneNumberId() {
        return phoneNumberId;
    }

    /**
     * Set phone number ID value
     *
     * @param phoneNumberId phone number ID value
     */
    public void setPhoneNumberId(int phoneNumberId) {
        this.phoneNumberId = phoneNumberId;
    }
}