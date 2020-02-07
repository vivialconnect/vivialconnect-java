package net.vivialconnect.model.error;

/**
 * Exception for responses without a payload. This is mainly use for deletion methods that do not return a payload
 * when was successful.
 *
 */
public class NoContentException extends RuntimeException{
	
    private static final long serialVersionUID = -6978518975975681292L;


    public NoContentException(){

    }


    public NoContentException(String message){
            super(message);
    }
}