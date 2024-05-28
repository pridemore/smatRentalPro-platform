package com.smatech.backendapiservice.errorhandler.exceptions;

public class UnauthorizedRequestException extends RuntimeException{
    public UnauthorizedRequestException(String message) {
        super(message);
    }
}
