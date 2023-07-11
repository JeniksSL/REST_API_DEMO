package com.rest_api_demo.facade.impl;


import com.rest_api_demo.domain.SubstanceEntity;
import com.rest_api_demo.dto.SubstanceDto;
import com.rest_api_demo.dto.mapper.core.DoubleMapper;
import com.rest_api_demo.dto.specification.SpecificationBuilder;
import com.rest_api_demo.facade.AbstractFacade;
import com.rest_api_demo.facade.SubstanceFacade;
import com.rest_api_demo.service.SubstanceService;
import org.springframework.stereotype.Service;


@Service
public class SubstanceFacadeImpl extends AbstractFacade<SubstanceDto, String,String, SubstanceEntity> implements SubstanceFacade {


    public SubstanceFacadeImpl(SubstanceService baseService,
                               DoubleMapper<SubstanceEntity, SubstanceDto> doubleMapper,
                               SpecificationBuilder<SubstanceEntity, String> specificationBuilder) {
        super(baseService, doubleMapper, specificationBuilder);
    }




}
