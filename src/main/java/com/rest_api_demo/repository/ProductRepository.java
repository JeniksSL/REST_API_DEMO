package com.manager.api.repository;


import com.manager.api.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends CrudRepository<ProductEntity, Integer>, JpaSpecificationExecutor<ProductEntity> {

}
