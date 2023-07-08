package com.rest_api_demo.controller;

import com.rest_api_demo.exceptions.AbstractException;
import com.rest_api_demo.exceptions.ErrorMessage;
import com.rest_api_demo.exceptions.IllegalRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice
public class BaseErrorHandlerController {


    @ExceptionHandler({HttpMessageNotReadableException.class, BindException.class,
            UnsatisfiedServletRequestParameterException.class, IllegalArgumentException.class,
            MethodArgumentTypeMismatchException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ErrorMessage> handleJsonMappingException(Exception ex) {
        /*
         * Exception occurs when passed id is null. Status 400.
         */
        return new ResponseEntity<>(
                new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "bad_request"),
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(IllegalRequestException.class)
    public ResponseEntity<List<String>> handleValidation(IllegalRequestException ex) {
        /*
         * Validation exceptions handling. Status code 400.
         */
        List<String> errors = new ArrayList<>();

        ex.getErrors().forEach(er -> errors.add(er.getField()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorMessage> handleAuthException(AuthenticationException ex) {
        /*
        Handles AuthenticationException exceptions.
        */
        return
                new ResponseEntity<>(
                        new ErrorMessage(
                                HttpStatus.FORBIDDEN.value(),
                                ex.getMessage()),
                        HttpStatus.FORBIDDEN
                );
    }

    @ExceptionHandler({AbstractException.class})
    public ResponseEntity<ErrorMessage> handleAbstractException(AbstractException ex) {
        /*
        Handles AbstractException exceptions.
        */
        return
                new ResponseEntity<>(
                        new ErrorMessage(
                                ex.getHttpCode(),
                                ex.getError()),
                        HttpStatus.valueOf(ex.getHttpCode())
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> processError(Exception ex) {
        /*
            500
         */
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "server_error"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
