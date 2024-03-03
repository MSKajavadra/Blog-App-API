package com.project.blogapp.exceptions;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(){
        super();
    }
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
