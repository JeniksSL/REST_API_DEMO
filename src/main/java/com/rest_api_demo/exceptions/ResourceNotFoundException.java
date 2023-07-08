package by.iba.common.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends AbstractException {

    public ResourceNotFoundException(String error) {
        super(HttpStatus.NOT_FOUND.value(), error, "object_not_found");
    }

    public ResourceNotFoundException(String error, String errorDescription) {
        super(HttpStatus.NOT_FOUND.value(), error, errorDescription);
    }

}
