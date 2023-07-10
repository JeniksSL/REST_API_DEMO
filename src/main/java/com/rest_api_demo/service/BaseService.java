package com.rest_api_demo.service;

import com.rest_api_demo.domain.core.BaseEntity;
import com.rest_api_demo.dto.core.BaseDto;
import com.rest_api_demo.service.core.EntityPatch;
import com.rest_api_demo.service.core.PageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;


public interface BaseService<E extends BaseEntity<ID>, ID> {

    E findById(ID id);
    Page<E> findAll(Integer page, Integer size);

    Page<E> findAll(Specification<E> specification, Integer page, Integer size);

    E save(E obj);

    void deleteById(ID id);

    E update(E obj, ID id);

    boolean existsById(ID id);






}
