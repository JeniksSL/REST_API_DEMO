package com.rest_api_demo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public final class ErrorMessage implements Serializable {


    private Integer httpCode;

    private String error;

}
