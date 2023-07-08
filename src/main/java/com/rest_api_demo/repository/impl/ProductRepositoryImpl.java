package com.rest_api_demo.repository.impl;



import com.rest_api_demo.domain.ProductEntity;
import com.rest_api_demo.repository.AbstractRepository;
import com.rest_api_demo.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;


@Repository
public class ProductRepositoryImpl extends AbstractRepository<ProductEntity, Long> implements ProductRepository {

    public ProductRepositoryImpl(EntityManager entityManager) {
        super(entityManager, ProductEntity.class);
    }







}
