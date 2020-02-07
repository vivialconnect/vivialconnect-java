package net.vivialconnect.model.error;

/**
 * Exception for Forbidden Access error returned by the API. This kind of error happens when the user try to access
 * endpoints beyond its permission scope, for example, trying to do an admin-only operation.
 *
 */
public class ForbiddenAccessException extends VivialConnectException {

    public ForbiddenAccessException() {
    }

    public ForbiddenAccessException(int responseCode, String description, Throwable cause) {
        super(responseCode,description, cause);
    }

}
