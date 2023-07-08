package com.rest_api_demo.dto.mapper;

import com.rest_api_demo.domain.SubstanceEntity;
import com.rest_api_demo.dto.SubstanceDto;
import com.rest_api_demo.dto.mapper.core.AbstractDoubleMapper;
import org.springframework.stereotype.Component;

@Component
public class SubstanceMapper extends AbstractDoubleMapper<SubstanceEntity, SubstanceDto> {

    public SubstanceMapper() {
        super(SubstanceEntity.class, SubstanceDto.class);
    }






}
