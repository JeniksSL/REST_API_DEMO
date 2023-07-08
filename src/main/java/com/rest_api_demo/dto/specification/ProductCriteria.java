package com.rest_api_demo.dto.specification;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductSearchCriteria {
    private String name;
    private List<String> substances;
}
