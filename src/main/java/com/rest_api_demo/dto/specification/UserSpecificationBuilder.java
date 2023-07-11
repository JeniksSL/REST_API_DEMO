package com.rest_api_demo.dto.specification;

import com.rest_api_demo.domain.UserEntity;
import com.rest_api_demo.dto.UserDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UserSpecificationBuilder implements SpecificationBuilder<UserEntity, String> {
    @Override
    public Specification<UserEntity> build(String criteria) {
        return Specification
                .where((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                        .like(root.get("id"), "%" + criteria + "%"));
    }
}
