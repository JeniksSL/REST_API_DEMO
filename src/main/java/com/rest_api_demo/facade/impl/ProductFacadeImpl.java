package com.rest_api_demo.facade.impl;

import com.rest_api_demo.dto.ProductDto;
import com.rest_api_demo.dto.specification.ProductCriteria;
import com.rest_api_demo.facade.ProductFacade;
import com.rest_api_demo.service.ProductService;
import com.rest_api_demo.service.core.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class ProductFacadeImpl implements ProductFacade {


    private final ProductService productService;

    @Override
    public ResponseEntity<ProductDto> get(Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @Override
    public ResponseEntity<PageDto<ProductDto>> get(Integer page, Integer size) {
        return ResponseEntity.ok(productService.findAll(page, size));
    }

    @Override
    public ResponseEntity<PageDto<ProductDto>> get(ProductCriteria criteria, Integer page, Integer size) {
        return ResponseEntity.ok(productService.findAll(criteria, page, size));
    }

    @Override
    public ResponseEntity<ProductDto> post(ProductDto obj) {
        final ProductDto saved = productService.save(obj);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/products/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(saved);
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<ProductDto> put(ProductDto obj, Long id) {
        return ResponseEntity.ok(productService.update(obj, id));
    }
}
