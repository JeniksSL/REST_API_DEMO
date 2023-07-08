package com.rest_api_demo.controller;

import com.rest_api_demo.domain.RoleType;
import com.rest_api_demo.dto.UserCompact;
import com.rest_api_demo.dto.UserDto;
import com.rest_api_demo.security.UserPrincipal;
import com.rest_api_demo.service.UserService;
import com.rest_api_demo.service.core.ControllerUtil;
import com.rest_api_demo.service.core.PageDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;



    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @GetMapping
    ResponseEntity<PageDto<UserDto>> findAllByCriteria(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                       @RequestParam(defaultValue = "10", required = false) Integer size,
                                                       @RequestBody(required = false) String userId) {
              return ResponseEntity.ok(userService.findAllByEmail(userId, page, size));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @GetMapping("/{id}")
    ResponseEntity<UserDto> findById(@PathVariable("id") String userId){
        return ResponseEntity.ok(userService.findById(userId));
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/roles")
    ResponseEntity<List<String>> getRoles() {
        return ResponseEntity.ok(Arrays.stream(RoleType.values()).map(Enum::name).toList());
    }

    @PostMapping
    ResponseEntity<UserDto> save(@Valid @RequestBody UserCompact userCompact,
                                 final BindingResult bindingResult) {
        ControllerUtil.checkBindingResult(bindingResult);
        final UserDto saved = userService.save(userCompact);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/{id}")
                .buildAndExpand(saved.getEmail())
                .toUri();

        return ResponseEntity.created(location).body(saved);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PutMapping("/update-roles/{id}")
    ResponseEntity<UserDto> updateRoles(@RequestBody(required = false) TreeSet<String> roles,
                                        @PathVariable("id") String userId) {
        return ResponseEntity.ok(userService.updateRoles(roles, userId));
    }

    @PutMapping("/password")
    ResponseEntity<UserDto> updatePassword(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                           @Valid @RequestBody UserCompact userCompact,
                                           final BindingResult bindingResult) {
        ControllerUtil.checkBindingResult(bindingResult);
        return ResponseEntity.ok(userService.updatePassword(userCompact, userPrincipal));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @DeleteMapping("/delete/{email}")
    ResponseEntity<Void> delete(@PathVariable("email") String email) {
        userService.deleteById(email);
        return ResponseEntity.ok().build();
    }


}