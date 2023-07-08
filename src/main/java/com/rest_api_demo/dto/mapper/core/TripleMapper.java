package com.rest_api_demo.dto.mapper.core;

import com.rest_api_demo.domain.core.BaseEntity;
import com.rest_api_demo.dto.core.BaseDto;
import com.rest_api_demo.dto.core.CompactDto;

public interface TripleMapper<E extends BaseEntity<?>, D extends BaseDto, C extends CompactDto> extends DoubleMapper<E, D> {
    E toEntityFromCompact(final C c);

}
