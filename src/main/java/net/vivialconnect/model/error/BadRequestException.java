package net.vivialconnect.model.error;

/**
 * Exception for Bad Request error returned by the API.
 *
 * This exception will be thrown for a response with HTTP status 400.
 */
public class BadRequestException extends VivialConnectException {

    public BadRequestException() {
    }

    public BadRequestException(int responseCode, String message, Throwable cause) {
        super(responseCode, message, cause);
    }
}
