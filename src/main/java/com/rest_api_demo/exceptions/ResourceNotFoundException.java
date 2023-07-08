package com.rest_api_demo.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends AbstractException {

    public ResourceNotFoundException(String error) {
        super(HttpStatus.NOT_FOUND.value(), error);
    }

}
