package net.vivialconnect.model.error;


public class NoContentException extends RuntimeException{
	
    private static final long serialVersionUID = -6978518975975681292L;


    public NoContentException(){

    }


    public NoContentException(String message){
            super(message);
    }
}