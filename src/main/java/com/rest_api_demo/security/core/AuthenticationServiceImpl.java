package com.rest_api_demo.security.core;


import com.rest_api_demo.exceptions.SecurityException;
import com.rest_api_demo.security.dto.JwtRequest;
import com.rest_api_demo.security.dto.JwtResponse;
import com.rest_api_demo.security.dto.TokenType;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserDetailsService userService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtService jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public JwtResponse login(@NonNull JwtRequest authRequest) {
        final UserDetails user = userService.loadUserByUsername(authRequest.getEmail());
        if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getUsername(), refreshToken);
            return new JwtResponse(TokenType.ACCESS_REFRESH.name(), accessToken, refreshToken);
        } else {
            throw new SecurityException(HttpStatus.FORBIDDEN.value(), "Wrong password");
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(email);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserDetails user = userService.loadUserByUsername(email);
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(TokenType.ACCESS.name(), accessToken, null);
            }
        }
        throw new SecurityException(HttpStatus.FORBIDDEN.value(),"Invalid JWT token");
    }

    public JwtResponse refresh(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(email);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserDetails user = userService.loadUserByUsername(email);
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getUsername(), newRefreshToken);
                return new JwtResponse(TokenType.ACCESS_REFRESH.name(), accessToken, newRefreshToken);
            }
        }
        throw new SecurityException(HttpStatus.FORBIDDEN.value(),"Invalid JWT token");
    }


}