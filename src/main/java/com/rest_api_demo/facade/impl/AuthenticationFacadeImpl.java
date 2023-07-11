package com.rest_api_demo.facade.impl;


import com.rest_api_demo.exceptions.ServiceException;
import com.rest_api_demo.facade.AuthenticationFacade;
import com.rest_api_demo.security.core.AuthenticationService;
import com.rest_api_demo.security.dto.AuthRequest;
import com.rest_api_demo.security.dto.JwtTokenWrapper;
import com.rest_api_demo.security.dto.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AuthenticationFacadeImpl implements AuthenticationFacade {


    private final AuthenticationService authenticationService;

    @Override
    public JwtTokenWrapper login(AuthRequest authRequest) {
        return authenticationService.login(authRequest);
    }


    @Override
    public JwtTokenWrapper getToken(JwtTokenWrapper tokenRequest) {
        validateTokenType(tokenRequest);
        return
        switch (TokenType.valueOf(tokenRequest.getType())){
            case ACCESS ->authenticationService.getAccessToken(tokenRequest.getRefreshToken());
            case REFRESH-> authenticationService.refresh(tokenRequest.getRefreshToken());
            default -> throw new ServiceException(HttpStatus.BAD_REQUEST.value(), "Wrong type");
        };
    }





    private void validateTokenType(final JwtTokenWrapper jwtTokenWrapper){
        if(!Arrays.stream(TokenType.values()).map(Enum::name).collect(Collectors.toSet()).contains(jwtTokenWrapper.getType()))
            throw new ServiceException(HttpStatus.BAD_REQUEST.value(), "No such type");
    }




}
