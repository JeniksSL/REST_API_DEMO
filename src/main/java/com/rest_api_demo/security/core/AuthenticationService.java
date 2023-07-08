package com.rest_api_demo.security.service;

import lombok.NonNull;

public interface AuthenticationService {

    JwtResponse login(@NonNull JwtRequest authRequest);

    public JwtResponse getAccessToken(@NonNull String refreshToken);

    public JwtResponse refresh(@NonNull String refreshToken);
}