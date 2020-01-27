package net.vivialconnect.model.error;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Map errors that occur at API-level.
 * <p>
 * The API has two kind of errors:
 * <ul>
 *     <li>Errors with error code and description</li>
 *     <li>Errors with a description only</li>
 * </ul>
 * <p>
 * When an error returns only the description the error code will be "empty"(in this case, it will be zero), otherwise, if
 * the error contains an error code and error description the two values will be filled accordingly.
 * <p>
 * For more info about errors with description and error code visit:
 * <a href="https://dashboard.vivialconnect.net/docs/api/errors.html">Error Codes and Rate Limits</a>
 */
public class ErrorMessage {
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

    public ErrorMessage() {
    }

    /**
     * Convenient constructor for create an instance of ErrorMessage with the error description and error code
     *
     * @param errorMessage error description value
     * @param errorCode    error code value
     */
    public ErrorMessage(String errorMessage, int errorCode) {
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