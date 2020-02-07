package net.vivialconnect.model.error;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *Intermediary class for parse errors that occur at API-level.
 * The values of this class are used for populate the VivialConnectException when an error occurs.
 * @see VivialConnectException
 */
public class MessageError {
    /**
     * Error description
     */
    @JsonProperty("message")
    private String errorMessage;
    /**
     * Numerical error codes
     */
    @JsonProperty("error_code")
    private int errorCode;

    public MessageError() {
    }

    /**
     * Convenient constructor for create an instance of ErrorMessage with the error description and error code
     *
     * @param errorMessage error description value
     * @param errorCode    error code value
     */
    public MessageError(String errorMessage, int errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    /**
     * Error message
     *
     * @return error message value
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Set error description
     *
     * @param errorMessage error description value
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Error code
     *
     * @return error code value
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Set error code value
     *
     * @param errorCode error code value
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}