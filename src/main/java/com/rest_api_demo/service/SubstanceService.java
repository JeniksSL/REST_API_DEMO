package com.rest_api_demo.service;

import com.rest_api_demo.domain.SubstanceEntity;
import com.rest_api_demo.dto.SubstanceDto;
import com.rest_api_demo.service.core.PageDto;

public interface SubstanceService extends BaseService<SubstanceEntity,SubstanceDto, String>{

    PageDto<SubstanceDto> findAllByName(String name, Integer page, Integer size);
}
