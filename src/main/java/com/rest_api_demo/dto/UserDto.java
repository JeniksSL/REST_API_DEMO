package com.rest_api_demo.dto;


import com.rest_api_demo.dto.core.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends BaseDto {

    private String email;

    private transient String password;

    private Set<String> roles;

    private Set<Long> products;


}
