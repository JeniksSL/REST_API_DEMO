package com.rest_api_demo.dto.mapper;

import com.rest_api_demo.domain.ProductEntity;
import com.rest_api_demo.domain.UserEntity;
import com.rest_api_demo.dto.UserCompact;
import com.rest_api_demo.dto.UserDto;
import com.rest_api_demo.dto.mapper.core.AbstractTripleMapperFromCompact;
import com.rest_api_demo.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class UserMapperFromCompact extends AbstractTripleMapperFromCompact<UserEntity, UserDto, UserCompact> {


    public UserMapperFromCompact(ProductRepository productRepository) {
        super(UserEntity.class, UserDto.class);
        this.productRepository = productRepository;
    }

    private final ProductRepository productRepository;

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(UserEntity.class, UserDto.class)
                .addMappings(m -> m.skip(UserDto::setProducts)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(UserDto.class, UserEntity.class)
                .addMappings(m -> m.skip( UserEntity::setProducts)).setPostConverter(toEntityConverter());
        mapper.createTypeMap(UserCompact.class, UserEntity.class)
                .setPostConverter(toEntityFromCompactConverter());
    }

    @Override
    protected void mapSpecificFields(final UserEntity source, final UserDto destination) {
        destination.setProducts(
                Optional.ofNullable(source.getProducts()).orElse(Collections.emptyList())
                        .stream()
                        .map(ProductEntity::getId)
                        .collect(Collectors.toSet())
        );
    }

    @Override
    protected void mapSpecificFields(final UserDto source, final UserEntity destination) {
        destination.setProducts(Optional.ofNullable(source.getProducts()).orElse(Collections.emptySet())
                        .stream().map(productRepository::findById).
                        filter(Optional::isPresent).map(Optional::get).toList()
        );
    }
}
