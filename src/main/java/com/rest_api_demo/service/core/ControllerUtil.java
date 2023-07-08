package com.rest_api_demo.service.core;

import com.rest_api_demo.exceptions.IllegalRequestException;
import org.springframework.validation.BindingResult;

import java.util.Objects;

public final class ControllerUtil {

    private ControllerUtil() {
    }
    public static void checkBindingResult(final BindingResult result) {
        if (Objects.nonNull(result)
                && result.hasErrors()) {
            throw new IllegalRequestException(result.getFieldErrors());
        }
    }




}
