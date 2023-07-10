package com.rest_api_demo.facade.impl;

import com.rest_api_demo.dto.UserCompact;
import com.rest_api_demo.dto.UserDto;
import com.rest_api_demo.exceptions.ServiceException;
import com.rest_api_demo.facade.UserFacade;
import com.rest_api_demo.service.UserService;
import com.rest_api_demo.service.core.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@Service
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;

    @Override
    public ResponseEntity<UserDto> get(String id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Override
    public ResponseEntity<PageDto<UserDto>> get(Integer page, Integer size) {
        return null;
    }

    @Override
    public ResponseEntity<PageDto<UserDto>> get(String criteria, Integer page, Integer size) {
        return ResponseEntity.ok(userService.findAllByEmail(criteria, page, size));
    }

    @Override
    public ResponseEntity<UserDto> postCompact(UserCompact obj) {
        final UserDto saved = userService.save(obj);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/{id}")
                .buildAndExpand(saved.getEmail())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @Override
    public ResponseEntity<UserDto> post(UserDto obj) {
        throw new ServiceException(HttpStatus.NOT_IMPLEMENTED.value(), "Not implemented");
    }

    @Override
    public ResponseEntity<Void> delete(String id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<UserDto> put(UserDto obj, String id) {
        return ResponseEntity.ok(userService.updateRoles(obj.getRoles(), id));
    }

    @Override
    public ResponseEntity<UserDto> patch(UserCompact obj, String id) {
        return ResponseEntity.ok(userService.updatePassword(obj, id));
    }
}
