package net.vivialconnect.model.error;

/**
 * Exception when a rate limit is reached in a API endpoint. Rate limits are enforced on all API endpoints.
 *
 * For more info about, visit the Vivial Connect Documentation, section:
 *  <a href="https://dashboard.vivialconnect.net/docs/api/errors.html#">Error Codes & Rate Limits</a>
 *
 */
public class RateLimitException extends ApiRequestException {

    public RateLimitException() {
    }

    public RateLimitException(int responseCode, String description, Throwable cause) {
        super(responseCode,description, cause);
    }

}
