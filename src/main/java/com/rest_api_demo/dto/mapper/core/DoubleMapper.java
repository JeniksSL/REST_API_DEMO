package com.rest_api_demo.dto.mapper.core;

import com.rest_api_demo.domain.core.BaseEntity;
import com.rest_api_demo.dto.core.BaseDto;

public interface DoubleEntityMapper<E extends BaseEntity<?>, D extends BaseDto> {

    E toEntity(final D d);
    D toDto(final E e);
}
