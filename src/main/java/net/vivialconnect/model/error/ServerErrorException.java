package net.vivialconnect.model.error;

/**
 * Exception when a server error happens.
 *
 * This exception is uncommon, but it can occurs in rare conditions.
 */
public class ServerErrorException extends VivialConnectException {

    public ServerErrorException() {
    }

    public ServerErrorException(int responseCode, String description, Throwable cause) {
        super(responseCode,description, cause);
    }

}
