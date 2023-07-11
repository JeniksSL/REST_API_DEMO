package com.rest_api_demo.facade;

import com.rest_api_demo.dto.UserCompact;
import com.rest_api_demo.dto.UserDto;

import java.util.List;

public interface UserFacade extends BaseFacade<UserDto, String,  String>  {
    UserDto save(UserCompact userCompact);

    UserDto update(UserCompact userCompact, String userId);

    List<String> findAllRoles();
}
