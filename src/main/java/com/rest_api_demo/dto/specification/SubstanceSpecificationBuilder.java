package com.rest_api_demo.dto.specification;

import com.rest_api_demo.domain.SubstanceEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class SubstanceSpecificationBuilder implements SpecificationBuilder<SubstanceEntity, String>{
    @Override
    public Specification<SubstanceEntity> build(String criteria) {
        return Specification
                .where((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                        .like(root.get("name"), "%" + criteria + "%"));
    }
}
