package by.iba.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AbstractException extends RuntimeException {

    private final Integer httpCode;

    private final String error; //for locale message

    private final String errorDescription; //for debugging

}
