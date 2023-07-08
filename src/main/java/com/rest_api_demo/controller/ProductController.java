package com.rest_api_demo.controller;

import com.rest_api_demo.dto.ProductDto;
import com.rest_api_demo.dto.specification.ProductCriteria;
import com.rest_api_demo.security.UserPrincipal;
import com.rest_api_demo.service.ProductService;
import com.rest_api_demo.service.core.ControllerUtil;
import com.rest_api_demo.service.core.PageDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;



    @GetMapping
    ResponseEntity<PageDto<ProductDto>> findAllByCriteria(@AuthenticationPrincipal UserPrincipal principal,
                                                          @RequestParam(defaultValue = "0", required = false) Integer page,
                                                          @RequestParam(defaultValue = "10", required = false) Integer size,
                                                          @Valid @RequestBody ProductCriteria criteria) {
        return ResponseEntity.ok(productService.findAll(criteria, page, size, principal));
    }

    @GetMapping("/{id}")
    ResponseEntity<ProductDto> findById(@AuthenticationPrincipal UserPrincipal principal,
                                        @PathVariable("id") Long productId) {
        return ResponseEntity.ok(productService.findById(productId, principal));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @GetMapping("/all")
    ResponseEntity<PageDto<ProductDto>> findAll(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                @RequestParam(defaultValue = "10", required = false) Integer size) {
        return ResponseEntity.ok(productService.findAll(page, size));
    }

    @PostMapping
    ResponseEntity<ProductDto> save(@AuthenticationPrincipal UserPrincipal principal,
                                    @Valid @RequestBody ProductDto productDto,
                                    final BindingResult bindingResult) {
        ControllerUtil.checkBindingResult(bindingResult);

        final ProductDto saved = productService.save(productDto, principal);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/products/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(saved);
    }


    @PutMapping("/{id}")
    ResponseEntity<ProductDto> update(@AuthenticationPrincipal UserPrincipal principal,
                                      @Valid @RequestBody ProductDto productDto,
                                      @PathVariable("id") Long productId,
                                      final BindingResult bindingResult) {
        ControllerUtil.checkBindingResult(bindingResult);
        return ResponseEntity.ok(productService.update(productDto, productId, principal));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@AuthenticationPrincipal UserPrincipal principal,
                                @PathVariable("id") Long productId) {
        productService.deleteById(productId, principal);
        return ResponseEntity.ok().build();
    }




}