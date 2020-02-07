package net.vivialconnect.model.error;

import java.io.IOException;

/**
 * Map errors that occur at API-level.

 * For more info about errors with description and error code visit:
 * <a href="https://dashboard.vivialconnect.net/docs/api/errors.html">Error Codes and Rate Limits</a>
 */
public class VivialConnectException extends Exception {

    private static final long serialVersionUID = -5461533988163106640L;
    /**
     * HTTP error code
     */
    private int responseCode;


    public VivialConnectException() {
    }


    public VivialConnectException(String message, Throwable cause) {
        super(message, cause);
    }

    public VivialConnectException(int responseCode, String message, Throwable cause) {
        super(message, cause);
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

}