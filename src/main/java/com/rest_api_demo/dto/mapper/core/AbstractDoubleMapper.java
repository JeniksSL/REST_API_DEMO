package com.rest_api_demo.dto.mapper.core;



import com.rest_api_demo.domain.UserEntity;
import com.rest_api_demo.domain.core.BaseEntity;
import com.rest_api_demo.dto.UserDto;
import com.rest_api_demo.dto.core.BaseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@RequiredArgsConstructor
public abstract class AbstractDoubleEntityMapper<E extends BaseEntity<?>, D extends BaseDto>
        implements DoubleEntityMapper<E, D> {
    private final Class<E> entityClass;
    private final Class<D> dtoClass;

    @Autowired
    protected ModelMapper mapper;

    @Override
    public E toEntity(final D dto) {
        return Objects.isNull(dto)
                ? null
                : mapper.map(dto, entityClass);
    }

    @Override
    public D toDto(final E entity) {
        return Objects.isNull(entity)
                ? null
                : mapper.map(entity, dtoClass);
    }


    protected Converter<E, D> toDtoConverter() {
        return context -> {
            E source = context.getSource();
            D destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    protected Converter<D, E> toEntityConverter() {
        return context -> {
            D source = context.getSource();
            E destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    protected void mapSpecificFields(final E source, final D destination) {
    }

    protected void mapSpecificFields(final D source, final E destination) {
    }


}
