package com.manager.api.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;


import java.util.Set;
import java.util.TreeSet;

public abstract class AbstractRepository<T, ID extends Comparable<ID>> extends SimpleJpaRepository<T, ID> {

    protected Class<T> clazz;
    protected EntityManager entityManager;


    public AbstractRepository(EntityManager entityManager, Class<T> clazz) {
        super(clazz, entityManager);
        this.entityManager = entityManager;
        this.clazz=clazz;}










}
