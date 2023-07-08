package com.rest_api_demo.security.core;

import com.rest_api_demo.security.dto.JwtRequest;
import com.rest_api_demo.security.dto.JwtResponse;
import lombok.NonNull;

public interface AuthenticationService {

    JwtResponse login(@NonNull JwtRequest authRequest);

    JwtResponse getAccessToken(@NonNull String refreshToken);

    JwtResponse refresh(@NonNull String refreshToken);
}