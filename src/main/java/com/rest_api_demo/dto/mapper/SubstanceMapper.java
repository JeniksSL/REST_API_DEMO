package com.rest_api_demo.dto.mapper;

import com.rest_api_demo.domain.SubstanceEntity;
import com.rest_api_demo.dto.SubstanceCompact;
import com.rest_api_demo.dto.SubstanceDto;
import com.rest_api_demo.dto.mapper.core.AbstractDoubleEntityMapper;
import org.springframework.stereotype.Component;

@Component
public class SubstanceEntityMapper extends AbstractDoubleEntityMapper<SubstanceEntity, SubstanceDto> {

    public SubstanceEntityMapper() {
        super(SubstanceEntity.class, SubstanceDto.class);
    }






}
