package com.rest_api_demo.service.impl;

import com.rest_api_demo.domain.ProductEntity;
import com.rest_api_demo.repository.ProductRepository;
import com.rest_api_demo.service.AbstractService;
import com.rest_api_demo.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends AbstractService<ProductEntity, Long> implements ProductService {


    public ProductServiceImpl(ProductRepository productRepository) {
        super(productRepository);
    }


    @Override
    public ProductEntity save(ProductEntity obj) {
        obj.setId(null);
        return super.save(obj);
    }


}
