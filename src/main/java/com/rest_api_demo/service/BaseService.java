package com.rest_api_demo.service;

import com.rest_api_demo.domain.core.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;


public interface BaseService<E extends BaseEntity<ID>, ID> {

    E findById(ID id);
    Page<E> findAll(Integer page, Integer size);

    Page<E> findAll(Specification<E> specification, Integer page, Integer size);

    E save(E obj);

    void deleteById(ID id);

    E update(E obj, ID id);

    boolean existsById(ID id);






}
