package com.rest_api_demo.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class ProductCriteria {
    private String name;
    private Set<String> substances;
    @NonNull
    @Email
    private String email;
    private Boolean isCommon;
}
