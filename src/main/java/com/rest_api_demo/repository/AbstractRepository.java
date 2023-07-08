package com.rest_api_demo.repository;


import com.rest_api_demo.domain.core.BaseEntity;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public abstract class AbstractRepository<T extends BaseEntity<ID>, ID > extends SimpleJpaRepository<T, ID> {

    protected Class<T> clazz;
    protected EntityManager entityManager;


    public AbstractRepository(EntityManager entityManager, Class<T> clazz) {
        super(clazz, entityManager);
        this.entityManager = entityManager;
        this.clazz=clazz;}










}
