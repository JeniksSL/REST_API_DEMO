package com.rest_api_demo.exceptions;

public class SecurityException extends AbstractException{

    public SecurityException(Integer httpCode, String error) {
        super(httpCode, error);
    }
}
