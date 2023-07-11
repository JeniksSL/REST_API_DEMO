package com.rest_api_demo.dto;


import com.rest_api_demo.dto.core.BaseDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends BaseDto {

    @NotBlank
    @Email
    private String email;

    private transient String password;

    @NotNull
    private Set<String> roles;

    private Set<Long> products;


}
