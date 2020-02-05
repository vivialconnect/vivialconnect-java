package net.vivialconnect.model.error;

/**
 * Exception when an error occurs during a message send.
 *
 * A message error contains an error code, a numerical value, and description, that allow to categorize and handle
 * the exception in specific ways.
 *
 * The list of possible error codes is in the Vivial Connect Documentation in the section
 * <a href="https://dashboard.vivialconnect.net/docs/api/errors.html#">Error Codes & Rate Limits</a>
 *
 */
public class MessageErrorException extends BadRequestException {

    private int errorCode;

    public MessageErrorException() {
    }

    public MessageErrorException(int errorCode, String message, int responseCode, Throwable cause) {
        super(responseCode, message, cause);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
