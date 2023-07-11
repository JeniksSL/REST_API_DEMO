package com.rest_api_demo.service;

import com.rest_api_demo.exceptions.ResourceNotFoundException;
import com.rest_api_demo.domain.core.BaseEntity;
import com.rest_api_demo.repository.BaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


@RequiredArgsConstructor
public abstract class AbstractService <E extends BaseEntity<ID>, ID> implements BaseService<E, ID> {

   private final BaseRepository<E,ID> baseRepository;
    public E findById(ID id){
       return baseRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("not found"));
    }

    public Page<E> findAll(Integer page, Integer size){
            Pageable pageable = PageRequest.of(page, size);
            return baseRepository.findAll(pageable);
    }
    public Page<E> findAll(Specification<E> specification, Integer page, Integer size){
        Pageable pageable = PageRequest.of(page, size);
        return baseRepository.findAll(specification,pageable);
    }

    public E save(E obj){
        return baseRepository.save(obj);
    }

    public void deleteById(ID id){
        if (!existsById(id))throw new ResourceNotFoundException("not found");
        baseRepository.deleteById(id);
    }
    public E update(E obj, ID id){
        E e= baseRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("not found"));
        obj.setId(id);
        return baseRepository.save(obj);
    }
    public boolean existsById(ID id){
        return baseRepository.existsById(id);
    }


}
