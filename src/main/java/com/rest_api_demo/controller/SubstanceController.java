package com.rest_api_demo.controller;

import com.rest_api_demo.dto.SubstanceCompact;
import com.rest_api_demo.dto.SubstanceDto;
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
@RequestMapping("/substances")
@RequiredArgsConstructor
public class SubstanceController {
    private final BaseFacade<SubstanceDto, String, SubstanceCompact, String, Void> facade;

    @GetMapping
    ResponseEntity<PageDto<SubstanceDto>> findAll(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                  @RequestParam(defaultValue = "10", required = false) Integer size) {
        return facade.get(page, size);
    }

    @GetMapping("/by-name")
    ResponseEntity<PageDto<SubstanceDto>> findAllByCriteria(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                       @RequestParam(defaultValue = "10", required = false) Integer size,
                                                       @RequestBody(required = false) String substanceId) {
        return facade.get(substanceId, page, size);
    }

    @GetMapping("/{id}")
    ResponseEntity<SubstanceDto> findById(@PathVariable("id") String substanceId){
        return facade.get(substanceId);
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @PostMapping
    ResponseEntity<SubstanceDto> save(@Valid @RequestBody SubstanceDto substanceDto,
                                      final BindingResult bindingResult) {
        ControllerUtil.checkBindingResult(bindingResult);
       return facade.post(substanceDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @PutMapping("/{id}")
    ResponseEntity<SubstanceDto> update(@RequestBody SubstanceDto substanceDto,
                                         @PathVariable("id") String substanceId) {
        return facade.put(substanceDto,substanceId);
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") String substanceId) {
        return facade.delete(substanceId);
    }


}
