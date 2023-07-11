package com.rest_api_demo.service.impl;

import com.rest_api_demo.domain.SubstanceEntity;
import com.rest_api_demo.repository.SubstanceRepository;
import com.rest_api_demo.service.AbstractService;
import com.rest_api_demo.service.SubstanceService;
import org.springframework.stereotype.Service;

@Service
public class SubstanceServiceImpl extends AbstractService<SubstanceEntity, String> implements SubstanceService {

    public SubstanceServiceImpl(SubstanceRepository baseRepository) {
        super(baseRepository);
    }


}
