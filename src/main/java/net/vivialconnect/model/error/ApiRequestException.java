package net.vivialconnect.model.error;

/**
 * This is exception is thrown when an error occurs during a API request, specifically when the error is not cause by:
 *
 * - Unauthorized access.
 * - Bad request.
 * - Forbidden access.
 * - Rate limit.
 * - Server error.
 * - Resource not found.
 */
public class ApiRequestException extends VivialConnectException {
    public ApiRequestException() {
    }

    public ApiRequestException(Throwable cause) {
        super(null, cause);
    }

    public ApiRequestException(int responseCode, String message, Throwable cause) {
        super(responseCode, message, cause);
    }
}
