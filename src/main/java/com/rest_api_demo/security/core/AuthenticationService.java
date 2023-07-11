package com.rest_api_demo.security.core;

import com.rest_api_demo.security.dto.AuthRequest;
import com.rest_api_demo.security.dto.JwtTokenWrapper;
import lombok.NonNull;

public interface AuthenticationService {

    JwtTokenWrapper login(@NonNull AuthRequest authRequest);

    JwtTokenWrapper getAccessToken(@NonNull String refreshToken);

    JwtTokenWrapper refresh(@NonNull String refreshToken);
}