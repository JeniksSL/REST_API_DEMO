package com.rest_api_demo.service.impl;

import com.rest_api_demo.domain.ProductEntity;
import com.rest_api_demo.dto.ProductDto;
import com.rest_api_demo.dto.mapper.core.DoubleMapper;
import com.rest_api_demo.dto.specification.ProductCriteria;
import com.rest_api_demo.dto.specification.ProductSpecificationBuilder;
import com.rest_api_demo.repository.ProductRepository;
import com.rest_api_demo.security.UserPrincipal;
import com.rest_api_demo.service.AbstractService;
import com.rest_api_demo.service.ProductService;
import com.rest_api_demo.service.core.PageDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends AbstractService<ProductEntity, ProductDto, Long> implements ProductService {



    public ProductServiceImpl(ProductRepository productRepository, DoubleMapper<ProductEntity, ProductDto> doubleMapper) {
        super(productRepository, doubleMapper);
    }

    @Override
    public PageDto<ProductDto> findAll(ProductCriteria criteria, Integer page, Integer size) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (UserPrincipal.isNotAdmin(principal)) {
            if (!criteria.getEmail().equals(principal.getEmail())) throw new SecurityException("User`s email is incorrect");
        }
        return super.findAll(ProductSpecificationBuilder.build(criteria), page, size);
    }

    @Override
    public ProductDto findById(Long id) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final ProductDto productDto = super.findById(id);
        if (UserPrincipal.isNotAdmin(principal)) {
            if (!productDto.getUserId().equals(principal.getEmail())||!productDto.getIsCommon())
                throw new SecurityException("Product is not available");
        }
        return productDto;
    }

    @Override
    public ProductDto save(ProductDto obj) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (UserPrincipal.isNotAdmin(principal)) {
            if (obj.getIsCommon()) throw new SecurityException("Can`t create common product");
        }
        obj.setUserId(principal.getEmail());
        return super.save(obj);
    }

    @Override
    public void deleteById(Long id) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final ProductDto productDto = super.findById(id);
        if (UserPrincipal.isNotAdmin(principal)) {
            if (!productDto.getUserId().equals(principal.getEmail()))
                throw new SecurityException("Product is not available");
        }
    }

    @Override
    public ProductDto update(ProductDto obj, Long id) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final ProductDto productDto = super.findById(id);
        if (UserPrincipal.isNotAdmin(principal)) {
            if (!productDto.getUserId().equals(principal.getEmail()))
                throw new SecurityException("Product is not available");
            if (obj.getIsCommon()) throw new SecurityException("Can`t create common product");
        }
        return super.save(obj);
    }

    @Override
    public PageDto<ProductDto> findAll(Integer page, Integer size) {
        return super.findAll(page, size);
    }
}
