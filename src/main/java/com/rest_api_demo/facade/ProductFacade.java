package com.rest_api_demo.facade;

import com.rest_api_demo.dto.ProductDto;
import com.rest_api_demo.dto.specification.ProductCriteria;

public interface ProductFacade extends  BaseFacade<ProductDto, Long, Void, ProductCriteria, Void>{
}
