package net.vivialconnect.model.error;

import java.io.IOException;

public class VivialConnectException extends Exception {

    private static final long serialVersionUID = -5461533988163106640L;

    private int responseCode;
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
     *     "message": "from_number invalid or not owned"
     * }
     * <p>
     * However, if the error was as an underlying {@link Exception} (eg, an {@link IOException} if there was no Internet connection),
     * this method will return the message of the underlying {@link Exception}.
     * 
     * @see #getCause()
     * 
     * @return the error message
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


    public void setResponseCode(int responseCode){
        this.responseCode = responseCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

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