package net.vivialconnect.model.error;

/**
 * Exception when an entity is not found. This error occurs for methods that retrieve a single entity using
 * an unique identifier, example, an ID or another value that is invalid.
 *
 * If the entity was already deleted, other request using the deleted entity ID will cause this exception.
 */
public class ResourceNotFoundException extends ApiRequestException {

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(int responseCode, String description, Throwable cause) {
        super(responseCode,description, cause);
    }

}
