package com.istv.airbnb.Search;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.function.Predicate;

public class LocationSpecification implements org.springframework.data.jpa.domain.Specification<Object> {

    private Predicate predicate;
    private CriteriaBuilder builder;
    private Root r;

    private SearchCriteria criteria;

    public LocationSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public javax.persistence.criteria.Predicate toPredicate(Root<Object> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThan(root.<String>get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThan(root.<String>get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("%")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(root.<String>get(criteria.getKey()), "%" + criteria.getValue().toString() + "%");
            } else
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
        } else if (criteria.getOperation().equalsIgnoreCase("=")) {
            return builder.equal(root.<String>get(criteria.getKey()), criteria.getValue());
        } else if (criteria.getOperation().equalsIgnoreCase("!=")) {
            return builder.notEqual(root.<String>get(criteria.getKey()), criteria.getValue());
        } else if (criteria.getOperation().equalsIgnoreCase("#")) {
            return builder.isNull(root.<String>get(criteria.getKey()));
        } else if (criteria.getOperation().equalsIgnoreCase("^")) {
            return builder.isNotNull(root.<String>get(criteria.getKey()));
        }
        return null;
    }

}

