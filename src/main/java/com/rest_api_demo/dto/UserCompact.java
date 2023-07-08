package com.rest_api_demo.dto;

import com.rest_api_demo.dto.core.CompactDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserCompact extends CompactDto {

    @NotBlank(message = "Email can not be blank")
    @Email(message = "Email is not valid")
    private String email;
    @NotBlank(message = "Password can not be blank")
    private String password;

    private String confirmedPassword;

}
