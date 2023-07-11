package com.rest_api_demo.facade;

import com.rest_api_demo.security.dto.AuthRequest;
import com.rest_api_demo.security.dto.JwtTokenWrapper;

public interface AuthenticationFacade {

    JwtTokenWrapper login(AuthRequest authRequest);

    JwtTokenWrapper getToken(JwtTokenWrapper tokenRequest);

}
