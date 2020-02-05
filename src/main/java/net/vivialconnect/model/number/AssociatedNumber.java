package net.vivialconnect.model.number;

import java.util.Date;
import java.util.Map;

import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.error.BadRequestException;
import net.vivialconnect.model.error.ServerErrorException;
import net.vivialconnect.model.error.ApiRequestException;
import net.vivialconnect.model.error.ForbiddenAccessException;
import net.vivialconnect.model.error.UnauthorizedAccessException;

/**
 * Interface that defined the basic methods that a number must type must have
 */
public interface AssociatedNumber extends INumber {

    /**
     * Must update the current values of the instance to the API
     *
     * @return AssociatedNumber instance
     * @throws VivialConnectException if an error occurs updating the number properties to the API
     */
    AssociatedNumber update() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException ;

    /**
     * Must update the current values of the instance to the API
     *
     * @return updated instance
     * @throws VivialConnectException if an error occurs updating the number properties to the API
     */
    AssociatedNumber updateLocalNumber() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException ;

    /**
     * Must delete the object of the API.
     *
     * @return true if the deletion was successful, false otherwise.
     * @throws VivialConnectException if an error occurs deleting the object from the API
     */
    boolean delete() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException ;

    /**
     * Must delete the object of the API.
     *
     * @return true if the deletion was successful, false otherwise.
     * @throws VivialConnectException if an error occurs deleting the object from the API
     */
    boolean deleteLocalNumber() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException ;

    /**
     * Must return information about the device type and carrier that is associated with a specific phone number
     *
     * @return Information about a phone number
     * @throws VivialConnectException if an error occurs getting the info from the API
     */
    NumberInfo lookup() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException ;

    /**
     * Must return object unique ID
     *
     * @return object unique ID value
     */
    int getId();

    /**
     * Must set object unique ID
     *
     * @param id object unique ID value
     */
    void setId(int id);

    /**
     * Must return creation Date
     *
     * @return creation date value
     */
    Date getDateCreated();

    /**
     * Must set the creation date
     *
     * @param dateCreated creation date value
     */
    void setDateCreated(Date dateCreated);

    /**
     * Must return modified date value
     *
     * @return modified date value
     */
    Date getDateModified();

    /**
     * Must set modified date
     *
     * @param dateModified modified date value
     */
    void setDateModified(Date dateModified);

    /**
     * Must return user's account ID
     *
     * @return user's account ID value
     */
    int getAccountId();

    /**
     * Must set user's account ID
     *
     * @param accountId account ID value
     */
    void setAccountId(int accountId);


    /**
     * Must return Voice Forwarding Number
     *
     * @return voice forwarding value
     */
    String getVoiceForwardingNumber();

    /**
     * Must set voice forwarding number
     *
     * @param voiceForwardingNumber voice forwarding value
     */
    void setVoiceForwardingNumber(String voiceForwardingNumber);


    /* Capabilities getCapabilities();


    void setCapabilities(Capabilities capabilities); */

    /**
     * Must return the instance active value
     *
     * @return active value
     */
    boolean isActive();

    /**
     * Must set the active value
     *
     * @param active active value
     */
    void setActive(boolean active);

    /**
     * Must return the URL status text
     *
     * @return URL status text
     */
    String getStatusTextUrl();

    /**
     * Must return incoming URL text
     *
     * @return incoming URL text value
     */
    String getIncomingTextUrl();

    /**
     * Must return Incoming text method
     *
     * @return incoming text value
     */
    String getIncomingTextMethod();

    /**
     * Must return incoming fallback text
     *
     * @return incoming text fallback value
     */
    String getIncomingTextFallbackUrl();

    /**
     * Must return incoming fallback method
     *
     * @return incoming fallback method value
     */
    String getIncomingTextFallbackMethod();

    /**
     * Must return the connector ID
     *
     * @return connector ID
     */
    int getConnectorId();

    /**
     * Must set Local address and transport area where the available phone number is located
     *
     * @param lata LATA value
     */
    void setLata(String lata);

    /**
     * Must set the city name
     *
     * @param city city value
     */
    void setCity(String city);

    /**
     * Must set the region name
     *
     * @param region region value
     */
    void setRegion(String region);

    /**
     * Must set the rate center.
     *
     * @param rateCenter rate center value
     */
    void setRateCenter(String rateCenter);

    /**
     * Must update the tags in the instance to the API.
     *
     * @param tags String key-pair values with the tag names and values
     * @return Updated tags
     */
    TagCollection updateTags(Map<String, String> tags) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException ;

    /**
     * Must pull the tags from the API and update them in the instance
     *
     * @return tags pulled from the API
     */
    TagCollection fetchTags() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException ;

    /**
     * Must delete the tags passed as parameter in the API
     *
     * @param tags tags to delete
     * @return remaining tags, if any, otherwise an empty collection
     */
    TagCollection deleteTags(Map<String, String> tags) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException ;
}