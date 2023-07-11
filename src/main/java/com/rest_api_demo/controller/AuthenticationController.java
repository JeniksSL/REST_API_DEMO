package com.rest_api_demo.controller;


import com.rest_api_demo.facade.AuthenticationFacade;
import com.rest_api_demo.security.dto.AuthRequest;
import com.rest_api_demo.security.dto.JwtTokenWrapper;
import com.rest_api_demo.service.core.ControllerUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationFacade authenticationFacade;

    @PostMapping({"/login"})
    public ResponseEntity<JwtTokenWrapper> login(@Valid @RequestBody AuthRequest authRequest,
                                                 final BindingResult bindingResult) {
        ControllerUtil.checkBindingResult(bindingResult);
        return ResponseEntity.ok(authenticationFacade.login(authRequest));
    }

    @PostMapping("/token")
    public ResponseEntity<JwtTokenWrapper> getNewToken(@RequestBody JwtTokenWrapper tokenRequest) {
        return ResponseEntity.ok(authenticationFacade.getToken(tokenRequest));
    }

}
