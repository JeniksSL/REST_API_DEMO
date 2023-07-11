package com.rest_api_demo.controller;

import com.rest_api_demo.dto.ProductDto;
import com.rest_api_demo.dto.ProductCriteria;
import com.rest_api_demo.facade.ProductFacade;
import com.rest_api_demo.service.core.ControllerUtil;
import com.rest_api_demo.service.core.PageDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {


    private final ProductFacade productFacade;

    @GetMapping("/by-criteria")
    ResponseEntity<PageDto<ProductDto>> findAllByCriteria(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                          @RequestParam(defaultValue = "10", required = false) Integer size,
                                                          @Valid @RequestBody ProductCriteria criteria) {
        return ResponseEntity.ok(productFacade.findAllByCriteria(criteria, page, size));
    }

    @GetMapping("/{id}")
    ResponseEntity<ProductDto> findById(@PathVariable("id") Long productId) {
        return ResponseEntity.ok(productFacade.findById(productId));
    }


    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @GetMapping
    ResponseEntity<PageDto<ProductDto>> findAll(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                @RequestParam(defaultValue = "10", required = false) Integer size) {
        return ResponseEntity.ok(productFacade.findAll(page, size));
    }

    @PostMapping
    ResponseEntity<ProductDto> save(@Valid @RequestBody ProductDto productDto,
                                    final BindingResult bindingResult) {
        ControllerUtil.checkBindingResult(bindingResult);
        final ProductDto saved = productFacade.save(productDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/products/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
       return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    ResponseEntity<ProductDto> update(@Valid @RequestBody ProductDto productDto,
                                      @PathVariable("id") Long productId,
                                      final BindingResult bindingResult) {
        ControllerUtil.checkBindingResult(bindingResult);
        return ResponseEntity.ok(productFacade.update(productDto,productId));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") Long productId) {
        productFacade.deleteById(productId);
        return  ResponseEntity.ok().build();
    }




}