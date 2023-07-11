package com.rest_api_demo.dto.specification;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder <E, CR> {
    Specification<E> build(CR criteria);
}
