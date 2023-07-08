package com.rest_api_demo.dto.mapper;

import com.rest_api_demo.domain.ProductEntity;
import com.rest_api_demo.dto.ProductDto;
import com.rest_api_demo.dto.SubstanceCompact;
import com.rest_api_demo.dto.mapper.core.AbstractDoubleMapper;
import com.rest_api_demo.repository.SubstanceRepository;
import com.rest_api_demo.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ProductMapper extends AbstractDoubleMapper<ProductEntity, ProductDto> {

    public ProductMapper(SubstanceRepository substanceRepository,  UserRepository userRepository) {
        super(ProductEntity.class, ProductDto.class);
        this.substanceRepository = substanceRepository;
        this.userRepository = userRepository;
    }

    private final SubstanceRepository substanceRepository;
    private final UserRepository userRepository;

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
        destination.setUserId(source.getUser().getId());
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
        destination.setUser(userRepository.findById(source.getUserId()).orElse(null));
    }

}
