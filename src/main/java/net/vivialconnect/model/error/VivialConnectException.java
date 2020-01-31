package net.vivialconnect.model.error;

import java.io.IOException;

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
public class VivialConnectException extends Exception {

    private static final long serialVersionUID = -5461533988163106640L;
    /**
     * HTTP error code
     */
    private int responseCode;

    /**
     * Platform error code
     */
    private int errorCode;


    public VivialConnectException() {
    }


    public VivialConnectException(String message, Throwable cause) {
        super(message, cause);
    }

    public VivialConnectException(int errorCode, String message, int responseCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.responseCode = responseCode;
    }


    public VivialConnectException(Throwable cause) {
        super(cause);
    }


    /**
     * Gets the error message.
     * <p>
     * If the error was one returned by the API, this method will return the string value
     * of the "message" property in the API error response, such as the one shown below:
     * <p>
     * {
     * "message": "from_number invalid or not owned"
     * }
     * <p>
     * However, if the error was as an underlying {@link Exception} (eg, an {@link IOException} if there was no Internet connection),
     * this method will return the message of the underlying {@link Exception}.
     *
     * @return the error message
     * @see #getCause()
     */
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    /**
     * Gets the HTTP response code.
     *
     * @return the HTTP response code
     */
    public int getResponseCode() {
        return responseCode;
    }

    /**
     * Set the HTTP response code
     *
     * @param responseCode HTTP response code value
     */
    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * Platform error code
     *
     * @return platform error code value
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Set platform error code
     *
     * @param errorCode platform error code value
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {

        String errorDescription = this.errorCode > 0 ?
                String.format("Error Code: %d - %s\n", this.errorCode, super.getMessage()) :
                super.getMessage();
        return errorDescription;
    }
}