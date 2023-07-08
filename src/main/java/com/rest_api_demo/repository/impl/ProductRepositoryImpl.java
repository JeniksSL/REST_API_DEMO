package com.manager.api.repository.impl;


import com.manager.api.model.ProductEntity;
import com.manager.api.repository.AbstractRepository;
import com.manager.api.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Repository;


@Repository
public class ProductRepositoryImpl extends AbstractRepository<ProductEntity, Integer> implements ProductRepository {

    public ProductRepositoryImpl(EntityManager entityManager) {
        super(entityManager, ProductEntity.class);
        System.out.println(entityManager);
    }







}
