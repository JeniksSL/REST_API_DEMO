package com.rest_api_demo.service;

import com.rest_api_demo.domain.core.BaseEntity;
import com.rest_api_demo.dto.core.BaseDto;
import com.rest_api_demo.service.core.PageDto;



public interface BaseService<E extends BaseEntity<ID>,D extends BaseDto, ID> {


    PageDto<D> findAll(Integer page, Integer size);

    D findById(ID id);

    D save(D obj);

    void deleteById(ID id);

    D update(D obj, ID id);



}
