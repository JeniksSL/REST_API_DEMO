package com.rest_api_demo.controller;


import com.rest_api_demo.dto.UserCompact;
import com.rest_api_demo.dto.UserDto;
import com.rest_api_demo.facade.BaseFacade;
import com.rest_api_demo.service.core.ControllerUtil;
import com.rest_api_demo.service.core.PageDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {


    private final BaseFacade<UserDto, String, UserCompact, String, UserCompact> facade;

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @GetMapping("/by-criteria")
    ResponseEntity<PageDto<UserDto>> findAllByCriteria(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                       @RequestParam(defaultValue = "10", required = false) Integer size,
                                                       @RequestBody(required = false) String userId) {
        return facade.get(userId, page, size);
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @GetMapping
    ResponseEntity<PageDto<UserDto>> findAll(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                       @RequestParam(defaultValue = "10", required = false) Integer size) {
        return facade.get(page, size);
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @GetMapping("/{id}")
    ResponseEntity<UserDto> findById(@PathVariable("id") String userId) {
        return facade.get(userId);
    }

    @PostMapping
    ResponseEntity<UserDto> save(@Valid @RequestBody UserCompact userCompact,
                                 final BindingResult bindingResult) {
        ControllerUtil.checkBindingResult(bindingResult);
        return facade.postCompact(userCompact);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PutMapping("/update-roles/{id}")
    ResponseEntity<UserDto> updateRoles(@RequestBody(required = false) UserDto userDto,
                                        @PathVariable("id") String userId) {
        return facade.put(userDto, userId);
    }

    @PatchMapping("/password/{id}")
    ResponseEntity<UserDto> updatePassword(@Valid @RequestBody UserCompact userCompact,
                                           @PathVariable("id") String userId,
                                           final BindingResult bindingResult) {
        ControllerUtil.checkBindingResult(bindingResult);
        return facade.patch(userCompact, userId);
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @DeleteMapping("/delete/{email}")
    ResponseEntity<Void> delete(@PathVariable("email") String email) {
        return facade.delete(email);
    }


}