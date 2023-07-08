package by.iba.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public final class ErrorMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer httpCode;

    private String error;

    private String errorDescription;

}
