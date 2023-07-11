package com.rest_api_demo.facade.impl;

import com.rest_api_demo.domain.RoleType;
import com.rest_api_demo.domain.UserEntity;
import com.rest_api_demo.dto.UserCompact;
import com.rest_api_demo.dto.UserDto;
import com.rest_api_demo.dto.mapper.core.TripleMapper;
import com.rest_api_demo.dto.specification.SpecificationBuilder;
import com.rest_api_demo.exceptions.SecurityException;
import com.rest_api_demo.exceptions.ServiceException;
import com.rest_api_demo.facade.AbstractFacade;
import com.rest_api_demo.facade.UserFacade;
import com.rest_api_demo.security.UserPrincipal;
import com.rest_api_demo.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserFacadeImpl extends AbstractFacade<UserDto,String,String, UserEntity> implements UserFacade {

    private final UserService userService;
    private final TripleMapper<UserEntity,UserDto, UserCompact> userMapper;

    public UserFacadeImpl(UserService baseService,
                          TripleMapper<UserEntity,UserDto, UserCompact> tripleMapper,
                          SpecificationBuilder<UserEntity, String> specificationBuilder) {
        super(baseService, tripleMapper, specificationBuilder);
        this.userService=baseService;
        this.userMapper=tripleMapper;
    }

    @Override
    public UserDto save(UserCompact userCompact) {
        final UserEntity saved = userService.save(userMapper.toEntityFromCompact(userCompact));
        return userMapper.toDto(saved);
    }

    @Override
    public UserDto save(UserDto obj) {
        throw new ServiceException(HttpStatus.METHOD_NOT_ALLOWED.value(), "Not allowed");
    }

    @Override
    public UserDto update(UserCompact userCompact, String userId) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!principal.getEmail().equals(userId)) throw new SecurityException(HttpStatus.FORBIDDEN.value(), "Wrong user's email");
        final UserEntity updated = userService.update(userMapper.toEntityFromCompact(userCompact), userId);
        return userMapper.toDto(updated);
    }

    @Override
    public UserDto update(UserDto userDto, String s) {

        validateRoles(userDto.getRoles());
        return super.update(userDto, s);
    }

    @Override
    public List<String> findAllRoles() {
        return Arrays.stream(RoleType.values()).map(Enum::name).toList();
    }


    private void validateRoles(final Set<String> roles){
        if(!Arrays.stream(RoleType.values()).map(Enum::name).collect(Collectors.toSet()).containsAll(roles))
            throw new ServiceException(HttpStatus.BAD_REQUEST.value(), "No such role");
    }

}
