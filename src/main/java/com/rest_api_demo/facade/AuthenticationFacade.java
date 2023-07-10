package com.rest_api_demo.facade;

import com.rest_api_demo.security.dto.JwtRequest;
import com.rest_api_demo.security.dto.JwtResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticationFacade extends BaseFacade<JwtResponse,Void,JwtRequest,Void,Void> {


}
