package com.aiinterview.platform.exception;

public class EmailAlreadyExistsException extends RuntimeException  {
   
    public EmailAlreadyExistsException(String message){
        super(message);
    }

    
}
