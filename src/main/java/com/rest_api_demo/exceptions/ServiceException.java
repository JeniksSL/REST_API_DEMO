package com.rest_api_demo.exceptions;


public class ServiceException extends AbstractException {
    public ServiceException(int value, String error) {
        super(value, error);
    }
}
