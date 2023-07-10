package com.rest_api_demo.facade;


import com.rest_api_demo.exceptions.ServiceException;
import com.rest_api_demo.service.core.PageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface BaseFacade<D, ID, CD, CR, P>{

    ResponseEntity<D> get(ID id);

    ResponseEntity<PageDto<D>>get(Integer page, Integer size);

    ResponseEntity<PageDto<D>>get(CR criteria, Integer page, Integer size);

    ResponseEntity<D> post(D obj);

    default ResponseEntity<D>  postCompact(CD obj){
        throw new ServiceException(HttpStatus.NOT_IMPLEMENTED.value(), "Not implemented");
    }

    ResponseEntity<Void> delete(ID id);

    ResponseEntity<D> put(D obj, ID id);

    default ResponseEntity<D> patch(P obj, ID id){
        throw new ServiceException(HttpStatus.NOT_IMPLEMENTED.value(), "Not implemented");
    }



}
