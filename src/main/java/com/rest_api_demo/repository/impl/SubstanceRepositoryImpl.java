package com.rest_api_demo.repository.impl;

import com.rest_api_demo.domain.SubstanceEntity;
import com.rest_api_demo.repository.AbstractRepository;
import com.rest_api_demo.repository.SubstanceRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class SubstanceRepositoryImpl extends AbstractRepository<SubstanceEntity, String> implements SubstanceRepository {
    public SubstanceRepositoryImpl(EntityManager entityManager) {
        super(entityManager, SubstanceEntity.class);
    }
}
