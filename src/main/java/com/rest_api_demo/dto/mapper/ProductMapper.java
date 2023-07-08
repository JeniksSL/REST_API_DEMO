package com.rest_api_demo.dto.mapper;

import com.rest_api_demo.domain.ProductEntity;
import com.rest_api_demo.domain.SubstanceEntity;
import com.rest_api_demo.dto.ProductDto;
import com.rest_api_demo.dto.SubstanceCompact;
import com.rest_api_demo.dto.SubstanceDto;
import com.rest_api_demo.dto.mapper.core.AbstractDoubleEntityMapper;
import com.rest_api_demo.dto.mapper.core.DoubleEntityMapper;
import com.rest_api_demo.repository.SubstanceRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ProductEntityMapper extends AbstractDoubleEntityMapper<ProductEntity, ProductDto> {

    public ProductEntityMapper(SubstanceRepository substanceRepository, SubstanceEntityMapper substanceEntityMapper) {
        super(ProductEntity.class, ProductDto.class);
        this.substanceRepository = substanceRepository;
    }

    private final SubstanceRepository substanceRepository;

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(ProductEntity.class, ProductDto.class)
                .addMappings(m -> m.skip(ProductDto::setUserId))
                .addMappings(m -> m.skip(ProductDto::setSubstanceSet))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(ProductDto.class, ProductEntity.class)
                .addMappings(m -> m.skip(ProductEntity::setUser))
                .addMappings(m -> m.skip(ProductEntity::setSubstanceMap))
                .setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(final ProductEntity source, final ProductDto destination) {
        destination.setSubstanceSet(
                Optional.ofNullable(source.getSubstanceMap()).orElse(Collections.emptyMap())
                        .entrySet().stream()
                        .map((entry)-> new SubstanceCompact(entry.getKey().getId(), entry.getValue()))
                        .collect(Collectors.toSet())
        );
    }

    @Override
    protected void mapSpecificFields(final ProductDto source, final ProductEntity destination) {
        destination.setSubstanceMap(Optional.ofNullable(source.getSubstanceSet()).orElse(Collections.emptySet())
                .stream()
                .collect(Collectors.toMap(
                        substance->substanceRepository.findById(substance.getId()).orElse(null),
                        SubstanceCompact::getContent)
                )
        );
    }

}
