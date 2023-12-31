package com.rest_api_demo.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenWrapper {
    private String type;
    private String accessToken;
    private String refreshToken;
}