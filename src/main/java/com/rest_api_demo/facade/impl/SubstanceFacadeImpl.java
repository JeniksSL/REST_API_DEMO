package com.rest_api_demo.facade.impl;


import com.rest_api_demo.dto.SubstanceDto;
import com.rest_api_demo.facade.SubstanceFacade;
import com.rest_api_demo.service.SubstanceService;
import com.rest_api_demo.service.core.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class SubstanceFacadeImpl implements SubstanceFacade {

    private final SubstanceService substanceService;


    @Override
    public ResponseEntity<SubstanceDto> get(String id) {
        return ResponseEntity.ok(substanceService.findById(id));
    }

    @Override
    public ResponseEntity<PageDto<SubstanceDto>> get(Integer page, Integer size) {
        return ResponseEntity.ok(substanceService.findAll(page, size));
    }

    @Override
    public ResponseEntity<PageDto<SubstanceDto>> get(String criteria, Integer page, Integer size) {
        return ResponseEntity.ok(substanceService.findAllByName(criteria, page, size));
    }

    @Override
    public ResponseEntity<SubstanceDto> post(SubstanceDto obj) {
        final SubstanceDto saved = substanceService.save(obj);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/substances/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @Override
    public ResponseEntity<Void> delete(String id) {
        substanceService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<SubstanceDto> put(SubstanceDto obj, String id) {
        return ResponseEntity.ok(substanceService.update(obj,id));
    }
}
