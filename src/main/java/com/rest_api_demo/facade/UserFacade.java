package com.rest_api_demo.facade;

import com.rest_api_demo.dto.UserCompact;
import com.rest_api_demo.dto.UserDto;

public interface UserFacade extends BaseFacade<UserDto, String, UserCompact, String, UserCompact>  {

}
