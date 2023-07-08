package com.rest_api_demo.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest {


    @Email(message = "Email is not valid")
    private String email;
    @NotBlank(message = "Password can not be blank")
    private String password;
}