package net.vivialconnect.model.error;

/**
 * Exception for unauthorized access error.
 *
 * All the request use an API key and an API secret provided by Vivial Connect underneath, if this exception is thrown
 * verify that these values and the account ID configured are correct.
 */
public class UnauthorizedAccessException extends VivialConnectException {
    public UnauthorizedAccessException() {
    }

    public UnauthorizedAccessException(int responseCode, String message, Throwable cause) {
        super(responseCode, message, cause);
    }
}
