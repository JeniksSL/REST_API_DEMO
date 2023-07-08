package com.rest_api_demo.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T, ID> extends JpaSpecificationExecutor<T> {

    List<T> findAll();

    Optional<T> findById(ID id);

    <S extends T> S save(S obj);
    boolean existsById(ID id);

    void deleteById(ID id);

    Page<T> findAll(Pageable pageable);

    Page<T> findAll(Specification<T> specification,Pageable pageable);

}
