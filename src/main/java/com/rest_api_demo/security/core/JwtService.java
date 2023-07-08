package com.rest_api_demo.security.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String generateAccessToken(UserDetails user);

    String generateRefreshToken(UserDetails user);

    boolean validateRefreshToken(String refreshToken);

    Claims getRefreshClaims(String refreshToken);

    boolean validateAccessToken(String token);

    Claims getAccessClaims(String token);
}
