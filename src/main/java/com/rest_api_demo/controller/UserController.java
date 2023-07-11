package com.rest_api_demo.controller;


import com.rest_api_demo.dto.UserCompact;
import com.rest_api_demo.dto.UserDto;
import com.rest_api_demo.facade.UserFacade;
import com.rest_api_demo.service.core.ControllerUtil;
import com.rest_api_demo.service.core.PageDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {


    private final UserFacade userFacade;

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @GetMapping("/by-criteria")
    ResponseEntity<PageDto<UserDto>> findAllByCriteria(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                       @RequestParam(defaultValue = "10", required = false) Integer size,
                                                       @RequestBody(required = false) String criteria) {
        return ResponseEntity.ok(userFacade.findAllByCriteria(criteria, page, size));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @GetMapping
    ResponseEntity<PageDto<UserDto>> findAll(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                       @RequestParam(defaultValue = "10", required = false) Integer size) {
        return ResponseEntity.ok(userFacade.findAll(page, size));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @GetMapping("/{id}")
    ResponseEntity<UserDto> findById(@PathVariable("id") String userId) {
        return ResponseEntity.ok(userFacade.findById(userId));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/roles")
    ResponseEntity<List<String>> findAllRoles() {
        return ResponseEntity.ok(userFacade.findAllRoles());
    }

    @PostMapping
    ResponseEntity<UserDto> save(@Valid @RequestBody UserCompact userCompact,
                                 final BindingResult bindingResult) {
        ControllerUtil.checkBindingResult(bindingResult);
        final UserDto saved = userFacade.save(userCompact);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/{id}")
                .buildAndExpand(saved.getEmail())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PutMapping("/update-roles/{id}")
    ResponseEntity<UserDto> updateRoles(@Valid @RequestBody UserDto userDto,
                                        @PathVariable("id") String userId,
                                        final BindingResult bindingResult) {
        ControllerUtil.checkBindingResult(bindingResult);
        return ResponseEntity.ok(userFacade.update(userDto,userId));
    }

    @PatchMapping("/password/{id}")
    ResponseEntity<UserDto> updatePassword(@Valid @RequestBody UserCompact userCompact,
                                           @PathVariable("id") String userId,
                                           final BindingResult bindingResult) {
        ControllerUtil.checkBindingResult(bindingResult);
        return ResponseEntity.ok(userFacade.update(userCompact,userId));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @DeleteMapping("/delete/{email}")
    ResponseEntity<Void> delete(@PathVariable("email") String email) {
        userFacade.deleteById(email);
        return ResponseEntity.ok().build();
    }


}