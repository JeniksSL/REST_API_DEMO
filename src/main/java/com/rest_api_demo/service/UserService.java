package com.rest_api_demo.service;

import com.rest_api_demo.domain.UserEntity;
import com.rest_api_demo.dto.UserCompact;
import com.rest_api_demo.dto.UserDto;
import com.rest_api_demo.service.core.PageDto;

import java.util.Set;

public interface UserService extends BaseService<UserEntity,UserDto, String>{
    UserDto save(UserCompact userCompact);

    UserDto updatePassword(UserCompact userCompact, String id);

    PageDto<UserDto> findAllByEmail(String email, Integer page, Integer size);

    UserDto updateRoles(Set<String> roles, String userId);
}
