package com.rest_api_demo.service;

import com.rest_api_demo.domain.SubstanceEntity;
import com.rest_api_demo.dto.SubstanceDto;
import com.rest_api_demo.dto.mapper.core.DoubleMapper;
import com.rest_api_demo.repository.SubstanceRepository;
import com.rest_api_demo.service.core.PageDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SubstanceServiceImpl extends AbstractService<SubstanceEntity,SubstanceDto , String> implements SubstanceService{

    public SubstanceServiceImpl(SubstanceRepository baseRepository, DoubleMapper<SubstanceEntity, SubstanceDto> doubleMapper) {
        super(baseRepository, doubleMapper);
    }

    @Override
    public PageDto<SubstanceDto> findAll(Integer page, Integer size) {
        return super.findAll(page, size);
    }


    @Override
    public  SubstanceDto findById(String s) {
        return super.findById(s);
    }

    @Override
    public  SubstanceDto save(SubstanceDto obj) {
        return super.save(obj);
    }


    @Override
    public  void deleteById(String s) {
        super.deleteById(s);
    }

    @Override
    public  SubstanceDto update(SubstanceDto substanceDto, String s) {
        return super.update(substanceDto, s);
    }
}
