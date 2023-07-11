package com.rest_api_demo.dto.specification;

import com.rest_api_demo.domain.ProductEntity;
import com.rest_api_demo.dto.ProductCriteria;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class ProductSpecificationBuilder implements SpecificationBuilder<ProductEntity, ProductCriteria> {

    public static Specification<ProductEntity> getByNameLike(String name) {
        if (name==null) return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.conjunction();

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<ProductEntity> getAllBySubstancesEqual(Set<String> substanceIds) {
        if (substanceIds==null||substanceIds.size()==0)
            return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.conjunction();

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String id : substanceIds) {
                Predicate pred = criteriaBuilder
                        .and(criteriaBuilder.equal(root.joinMap("substanceMap").key().get("id"), id));
                predicates.add(pred);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<ProductEntity> getByUser(String email, Boolean isCommon) {
        if (isCommon!=null&&isCommon) return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .or(criteriaBuilder.equal(root.get("user").get("id"), email ), criteriaBuilder.equal(root.get("is_common"), true));
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), email);
    }

    public Specification<ProductEntity> build(ProductCriteria criteria){
        return Specification.where(getByNameLike(criteria.getName())
                .and(getAllBySubstancesEqual(criteria.getSubstances())
                        .and(getByUser(criteria.getEmail(), criteria.getIsCommon()))));
    }
}
