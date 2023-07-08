package com.rest_api_demo.repository;



import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID extends Comparable<ID> > extends JpaSpecificationExecutor<T> {

    <S extends T> S saveAndFlush(S entity);
    List<T> findAll();

    Optional<T> findById(ID id);

    <S extends T> S save(S obj);


    void deleteById(ID id);

}
