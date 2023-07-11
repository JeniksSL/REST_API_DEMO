package com.rest_api_demo.facade;

import com.rest_api_demo.domain.core.BaseEntity;
import com.rest_api_demo.dto.core.BaseDto;
import com.rest_api_demo.dto.mapper.core.DoubleMapper;
import com.rest_api_demo.dto.specification.SpecificationBuilder;
import com.rest_api_demo.service.BaseService;
import com.rest_api_demo.service.core.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@RequiredArgsConstructor
public class AbstractFacade<D extends BaseDto, ID, CR, E extends BaseEntity<ID>> {

    private final BaseService<E, ID> baseService;
    private final DoubleMapper<E, D> doubleMapper;
    private final SpecificationBuilder<E, CR> specificationBuilder;


    public PageDto<D> findAll(Integer page, Integer size) {

        final Page<E> ePage = baseService.findAll(page, size);
        return new PageDto<>(ePage.stream().map(doubleMapper::toDto).toList(),
                ePage.getTotalPages(),
                ePage.getTotalElements());
    }

    public PageDto<D> findAllByCriteria(CR criteria, Integer page, Integer size) {
        final Page<E> ePage= baseService.findAll(specificationBuilder.build(criteria), page, size);
         return new PageDto<>(ePage.stream().map(doubleMapper::toDto).toList(),
                 ePage.getTotalPages(),
                 ePage.getTotalElements());
    }

    public D findById(ID id) {
        return doubleMapper.toDto(baseService.findById(id));
    }

    public D save(D obj){
        return doubleMapper.toDto(baseService.save(doubleMapper.toEntity(obj)));
    }

   public void deleteById(ID id) {
        baseService.deleteById(id);
    }

    public D update(D d, ID id) {
        return doubleMapper.toDto(baseService.update(doubleMapper.toEntity(d), id));
    }
}
