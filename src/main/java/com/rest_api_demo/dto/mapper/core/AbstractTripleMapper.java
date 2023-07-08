package com.rest_api_demo.dto.mapper.core;




import com.rest_api_demo.domain.core.BaseEntity;
import com.rest_api_demo.dto.core.BaseDto;
import com.rest_api_demo.dto.core.CompactDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@RequiredArgsConstructor
public abstract class AbstractTripleMapperFromCompact<E extends BaseEntity<?>, D extends BaseDto, C extends CompactDto>
        implements TripleMapperFromCompact<E, D, C> {
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

    @Override
    public E toEntityFromCompact(final C compact) {
        return Objects.isNull(compact)
                ? null
                : mapper.map(compact, entityClass);
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

    protected Converter<C, E> toEntityFromCompactConverter() {
        return context -> {
            C source = context.getSource();
            E destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    protected void mapSpecificFields(final E source, final D destination) {
    }

    protected void mapSpecificFields(final D source, final E destination) {
    }

    protected void mapSpecificFields(final C source, final E destination) {
    }

}