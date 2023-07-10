package com.rest_api_demo.facade.impl;


import com.rest_api_demo.exceptions.ServiceException;
import com.rest_api_demo.facade.AuthenticationFacade;
import com.rest_api_demo.security.core.AuthenticationService;
import com.rest_api_demo.security.dto.JwtRequest;
import com.rest_api_demo.security.dto.JwtResponse;
import com.rest_api_demo.security.dto.TokenType;
import com.rest_api_demo.service.core.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AuthenticationFacadeImpl implements AuthenticationFacade {


    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<JwtResponse> postCompact(JwtRequest jwtRequest) {
        return ResponseEntity.ok(authenticationService.login(jwtRequest));
    }


    @Override
    public ResponseEntity<JwtResponse> post(JwtResponse tokenRequest) {
        validateTokenType(tokenRequest);
        return
        switch (TokenType.valueOf(tokenRequest.getType())){
            case ACCESS ->ResponseEntity.ok(authenticationService.getAccessToken(tokenRequest.getRefreshToken()));
            case REFRESH-> ResponseEntity.ok(authenticationService.refresh(tokenRequest.getRefreshToken()));
            default -> throw new ServiceException(HttpStatus.BAD_REQUEST.value(), "Wrong type");
        };
    }


    @Override
    public ResponseEntity<Void> delete(Void unused) {
        throw new ServiceException(HttpStatus.NOT_IMPLEMENTED.value(), "Not implemented");
    }

    @Override
    public ResponseEntity<JwtResponse> put(JwtResponse obj, Void unused) {
        throw new ServiceException(HttpStatus.NOT_IMPLEMENTED.value(), "Not implemented");
    }

    @Override
    public ResponseEntity<JwtResponse> get(Void unused) {
        throw new ServiceException(HttpStatus.NOT_IMPLEMENTED.value(), "Not implemented");
    }

    @Override
    public ResponseEntity<PageDto<JwtResponse>> get(Integer page, Integer size) {
        throw new ServiceException(HttpStatus.NOT_IMPLEMENTED.value(), "Not implemented");
    }

    @Override
    public ResponseEntity<PageDto<JwtResponse>> get(Void criteria, Integer page, Integer size) {
        throw new ServiceException(HttpStatus.NOT_IMPLEMENTED.value(), "Not implemented");
    }


    private void validateTokenType(final JwtResponse jwtResponse){
        if(!Arrays.stream(TokenType.values()).map(Enum::name).collect(Collectors.toSet()).contains(jwtResponse.getType()))
            throw new ServiceException(HttpStatus.BAD_REQUEST.value(), "No such type");
    }




}
