package com.rest_api_demo.service;


import com.rest_api_demo.dto.ProductDto;
import com.rest_api_demo.dto.specification.ProductCriteria;
import com.rest_api_demo.security.UserPrincipal;
import com.rest_api_demo.service.core.PageDto;

public interface ProductService {

    PageDto<ProductDto> findAll(Integer page, Integer size);

    PageDto<ProductDto> findAll(ProductCriteria criteria, Integer page, Integer size, UserPrincipal principal);

    ProductDto findById(Long id, UserPrincipal principal);

    ProductDto save(ProductDto obj, UserPrincipal principal);


    void deleteById(Long id, UserPrincipal principal);

    ProductDto update(ProductDto obj, Long id, UserPrincipal principal);




}
