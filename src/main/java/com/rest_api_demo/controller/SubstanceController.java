package com.rest_api_demo.controller;

import com.rest_api_demo.dto.SubstanceDto;
import com.rest_api_demo.service.SubstanceService;
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
@RequestMapping("/substances")
@RequiredArgsConstructor
public class SubstanceController {

    private final SubstanceService substanceService;


    @GetMapping
    ResponseEntity<PageDto<SubstanceDto>> findAll(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                  @RequestParam(defaultValue = "10", required = false) Integer size) {
        return ResponseEntity.ok(substanceService.findAll(page, size));
    }

    @GetMapping("/{id}")
    ResponseEntity<SubstanceDto> findById(@PathVariable("id") String substanceId){
        return ResponseEntity.ok(substanceService.findById(substanceId));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @PostMapping
    ResponseEntity<SubstanceDto> save(@Valid @RequestBody SubstanceDto substanceDto,
                                      final BindingResult bindingResult) {
        ControllerUtil.checkBindingResult(bindingResult);
        final SubstanceDto saved = substanceService.save(substanceDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/substances/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @PutMapping("/{id}")
    ResponseEntity<SubstanceDto> update(@RequestBody SubstanceDto substanceDto,
                                         @PathVariable("id") String substanceId) {
        return ResponseEntity.ok(substanceService.update(substanceDto,substanceId));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") String substanceId) {
        substanceService.deleteById(substanceId);
        return ResponseEntity.ok().build();
    }


}
