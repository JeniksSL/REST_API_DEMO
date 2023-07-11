package com.rest_api_demo.facade.impl;

import com.rest_api_demo.exceptions.SecurityException;
import com.rest_api_demo.domain.ProductEntity;
import com.rest_api_demo.dto.ProductDto;
import com.rest_api_demo.dto.mapper.core.DoubleMapper;
import com.rest_api_demo.dto.ProductCriteria;
import com.rest_api_demo.dto.specification.SpecificationBuilder;
import com.rest_api_demo.facade.AbstractFacade;
import com.rest_api_demo.facade.ProductFacade;
import com.rest_api_demo.security.UserPrincipal;
import com.rest_api_demo.service.ProductService;
import com.rest_api_demo.service.core.PageDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class ProductFacadeImpl extends AbstractFacade<ProductDto, Long,ProductCriteria, ProductEntity> implements ProductFacade {


    public ProductFacadeImpl(ProductService baseService,
                             DoubleMapper<ProductEntity, ProductDto> doubleMapper,
                             SpecificationBuilder<ProductEntity, ProductCriteria> specificationBuilder) {
        super(baseService, doubleMapper, specificationBuilder);
    }

    @Override
    public PageDto<ProductDto> findAllByCriteria(ProductCriteria criteria, Integer page, Integer size) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (UserPrincipal.isNotAdmin(principal)) {
            if (!criteria.getEmail().equals(principal.getEmail())) throw new SecurityException(HttpStatus.FORBIDDEN.value(),"User`s email is incorrect");
        }
        return super.findAllByCriteria(criteria, page, size);
    }

    @Override
    public ProductDto findById(Long id) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ProductDto productDto = super.findById(id);
        if (UserPrincipal.isNotAdmin(principal)) {
            if (!productDto.getUserId().equals(principal.getEmail())||!productDto.getIsCommon())
                throw new SecurityException(HttpStatus.FORBIDDEN.value(),"Product is not available");
        }
        return productDto;
    }

    @Override
    public ProductDto save(ProductDto obj) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (UserPrincipal.isNotAdmin(principal)) {
            if (obj.getIsCommon()) throw new SecurityException(HttpStatus.FORBIDDEN.value(),"Can`t create common product");
        }
        obj.setUserId(principal.getEmail());
        return super.save(obj);
    }

    @Override
    public ProductDto update(ProductDto productDto, Long id) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final ProductDto original = super.findById(id);
        if (UserPrincipal.isNotAdmin(principal)) {
            if (!original.getUserId().equals(principal.getEmail()))
                throw new SecurityException(HttpStatus.FORBIDDEN.value(),"Product is not available");
            if (original.getIsCommon()) throw new SecurityException(HttpStatus.FORBIDDEN.value(),"Can`t create common product");
        }
        return super.update(productDto, id);
    }

    @Override
    public void deleteById(Long id) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final ProductDto productDto = super.findById(id);
        if (UserPrincipal.isNotAdmin(principal)) {
            if (!productDto.getUserId().equals(principal.getEmail()))
                throw new SecurityException(HttpStatus.FORBIDDEN.value(),"Product is not available");
        }
        super.deleteById(id);
    }
}
