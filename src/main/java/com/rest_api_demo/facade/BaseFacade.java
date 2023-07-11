package com.rest_api_demo.facade;


import com.rest_api_demo.dto.core.BaseDto;
import com.rest_api_demo.service.core.PageDto;


public interface BaseFacade<D extends BaseDto, ID, CR>{

    D findById(ID id);

    PageDto<D>findAll(Integer page, Integer size);

    PageDto<D>findAllByCriteria(CR criteria, Integer page, Integer size);

    D save(D obj);

    void deleteById(ID id);

    D update(D obj, ID id);


}
