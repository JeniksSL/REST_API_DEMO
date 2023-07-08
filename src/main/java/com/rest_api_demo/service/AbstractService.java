package com.rest_api_demo.service;

import com.rest_api_demo.exceptions.ResourceNotFoundException;
import com.rest_api_demo.domain.core.BaseEntity;
import com.rest_api_demo.dto.core.BaseDto;
import com.rest_api_demo.dto.mapper.core.DoubleMapper;
import com.rest_api_demo.repository.BaseRepository;
import com.rest_api_demo.service.core.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


@RequiredArgsConstructor
public abstract class AbstractService <E extends BaseEntity<ID>, D extends BaseDto, ID>  {

    private final BaseRepository<E, ID> baseRepository;
    private final DoubleMapper<E, D> doubleMapper;


    protected PageDto<D> findAll(Integer page, Integer size) {
        Pageable pageable =
                PageRequest.of(page, size);
        Page<E> ePage = baseRepository.findAll(pageable);
        return new PageDto<>(ePage.stream().map(doubleMapper::toDto).toList(),ePage.getTotalPages(),ePage.getTotalElements());
    }



    protected PageDto<D> findAll(Specification<E> specification, Integer page, Integer size) {
        Pageable pageable =
                PageRequest.of(page, size);
        Page<E> ePage = baseRepository.findAll(specification, pageable);
        return new PageDto<>(ePage.stream().map(doubleMapper::toDto).toList(),ePage.getTotalPages(),ePage.getTotalElements());
    }


    protected D findById(ID id) {
        E e= baseRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("not found"));
        return doubleMapper.toDto(e);
    }

    protected D save(D obj) {
        return doubleMapper.toDto(baseRepository.save(doubleMapper.toEntity(obj)));
    }



    protected void deleteById(ID id) {
        baseRepository.deleteById(id);
    }

    protected D update(D d, ID id) {
        E e= baseRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("not found"));
        E update = doubleMapper.toEntity(d);
        update.setId(e.getId());
        return doubleMapper.toDto(baseRepository.save(update));
    }
}
