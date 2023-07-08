package com.rest_api_demo.repository.impl;

import com.rest_api_demo.domain.UserEntity;
import com.rest_api_demo.repository.AbstractRepository;
import com.rest_api_demo.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends AbstractRepository<UserEntity, String> implements UserRepository {

    public UserRepositoryImpl(EntityManager entityManager) {
        super(entityManager, UserEntity.class);
    }
}
