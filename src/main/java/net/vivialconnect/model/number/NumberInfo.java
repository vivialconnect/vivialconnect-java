package net.vivialconnect.model.number;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import net.vivialconnect.model.VivialConnectResource;

/**
 * A class to hold information about the device type and carrier that is associated with a specific phone number.
 * <p>
 * This information can be useful for text messaging campaigns that target specific user experiences based on the
 * user’s phone number information.
 */
@JsonRootName(value = "number_info")
public class NumberInfo extends VivialConnectResource {

    private static final long serialVersionUID = 8828026309873208187L;

    /**
     * Details about the phone carrier providing service to the phone number
     */
    @JsonProperty
    private Carrier carrier;

    /**
     * Details about the device type used by the phone number
     */
    @JsonProperty
    private Device device;

    /**
     * Phone number for which you want device and carrier information
     */
    @JsonProperty("phone_number")
    private String phoneNumber;

    /**
     * Creation date (UTC) of the number info in ISO 8601 format
     */
    @JsonProperty("date_created")
    private Date dateCreated;

    /**
     * Last modification date (UTC) of number info in ISO 8601 format
     */
    @JsonProperty("date_modified")
    private Date dateModified;

    /**
     * Details about the phone carrier providing service to the phone number.
     *
     * @return carrier name value
     */
    public Carrier getCarrier() {
        return carrier;
    }

    /**
     * Set the carrier name value.
     * <strong>Note:</strong> Setting this value does not have any effect in the  API
     *
     * @param carrier carrier name value
     */
    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    /**
     * Details about the device type used by the phone number.
     * <p>
     * Results include:
     * <p>
     * - model - Model name of the device.
     * - error - Information about errors in device lookup. For example, number is not mobile or carrier doesn’t support device lookup.
     *
     * @return Device object with info about the device to which a number belongs
     */
    public Device getDevice() {
        return device;
    }

    /**
     * Set a Device object.
     * <strong>Note:</strong> Setting this value does not have any effect in the  API
     *
     * @param device a Device object
     */
    public void setDevice(Device device) {
        this.device = device;
    }

    /**
     * Phone number for which you want device and carrier information.
     *
     * @return a phone number value
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Set the phone number value
     * <strong>Note:</strong> Setting this value does not have any effect in the  API
     *
     * @param phoneNumber phone number value
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}