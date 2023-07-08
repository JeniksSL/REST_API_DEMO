package com.rest_api_demo.controller;


import com.rest_api_demo.security.core.AuthenticationService;
import com.rest_api_demo.security.dto.JwtRequest;
import com.rest_api_demo.security.dto.JwtResponse;
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

    private final AuthenticationService authenticationService;

    @PostMapping({"/login"})
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody JwtRequest authRequest,
                                             final BindingResult bindingResult) {
        ControllerUtil.checkBindingResult(bindingResult);
        return ResponseEntity.ok(authenticationService.login(authRequest));
    }

    @PostMapping("/token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody String token) {
        return ResponseEntity.ok(authenticationService.getAccessToken(token));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody String token) {
        return ResponseEntity.ok(authenticationService.refresh(token));
    }


}
