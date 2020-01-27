package net.vivialconnect.model.connector;

import net.vivialconnect.model.error.VivialConnectException;

import java.util.Date;
import java.util.List;

/**
 * This interface defines the basic properties and methods for a connector that contains a list phone numbers
 */
public interface ConnectorWithPhoneNumbers {
    /**
     * Must return the last modification date of the connector
     *
     * @return last modification date of the connector
     */
    Date getDateModified();

    /**
     * Must return count of phone numbers associated to the connector
     *
     * @return count of numbers associated to the connector
     */
    int getPhoneNumbersCount();

    /**
     * Must paginate to the next page and return its page number
     *
     * @return next page value
     * @throws VivialConnectException if an error occurs at API-level
     */
    int nextPage() throws VivialConnectException;

    /**
     * Must paginate to the previous page and return its page number
     *
     * @return previous page value
     * @throws VivialConnectException if an error occurs at API-level
     */
    int previousPage() throws VivialConnectException;

    /**
     * Must return the next page value
     *
     * @return next page value
     */
    int getNextPage();

    /**
     * Must return the previous page value
     *
     * @return previous page value
     */
    int getPreviousPage();

    /**
     * Must return count of pages
     *
     * @return count of pages value
     */
    int getPages();

    /**
     * Must return a list of phone numbers associated to a connector
     *
     * @return list of phone numbers associated to a connector
     */
    List<PhoneNumber> getPhoneNumbers();

}