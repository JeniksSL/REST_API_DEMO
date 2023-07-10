package com.rest_api_demo.controller;

import com.rest_api_demo.dto.ProductDto;
import com.rest_api_demo.dto.specification.ProductCriteria;
import com.rest_api_demo.facade.BaseFacade;
import com.rest_api_demo.service.core.ControllerUtil;
import com.rest_api_demo.service.core.PageDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {


    private final BaseFacade<ProductDto, Long, Void, ProductCriteria, Void> facade;

    @GetMapping("/by-criteria")
    ResponseEntity<PageDto<ProductDto>> findAllByCriteria(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                          @RequestParam(defaultValue = "10", required = false) Integer size,
                                                          @Valid @RequestBody ProductCriteria criteria) {
        return facade.get(criteria, page, size);
    }

    @GetMapping("/{id}")
    ResponseEntity<ProductDto> findById(@PathVariable("id") Long productId) {
        return facade.get(productId);
    }


    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @GetMapping
    ResponseEntity<PageDto<ProductDto>> findAll(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                @RequestParam(defaultValue = "10", required = false) Integer size) {
        return facade.get(page, size);
    }



    @PostMapping
    ResponseEntity<ProductDto> save(@Valid @RequestBody ProductDto productDto,
                                    final BindingResult bindingResult) {
        ControllerUtil.checkBindingResult(bindingResult);
       return facade.post(productDto);
    }


    @PutMapping("/{id}")
    ResponseEntity<ProductDto> update(@Valid @RequestBody ProductDto productDto,
                                      @PathVariable("id") Long productId,
                                      final BindingResult bindingResult) {
        ControllerUtil.checkBindingResult(bindingResult);
        return facade.put(productDto, productId);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") Long productId) {
        return  facade.delete(productId);
    }




}