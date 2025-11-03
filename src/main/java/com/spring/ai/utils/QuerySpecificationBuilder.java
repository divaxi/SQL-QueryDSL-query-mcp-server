package com.spring.ai.utils;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.ai.dto.Query;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate.BooleanOperator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class QuerySpecificationBuilder<T> {

    private final ObjectMapper objectMapper;

    public Specification<T> build(Query<T> query) {
        if (query == null) {
            return null;
        }
        return (root,cq,cb)-> buildPredicate(query,root,cb);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Predicate buildPredicate(Query<T> query, Root<T> root, CriteriaBuilder cb) {
        // Implement logic to convert Query object to Predicate
        // This is a placeholder implementation
        if (query.getConditions() != null && !query.getConditions().isEmpty()) {
            Predicate[] predicates = query.getConditions().stream()
                    .map(cond -> buildPredicate(cond, root, cb))
                    .toArray(Predicate[]::new);
            if (query.getOperator()== null|| query.getOperator() == BooleanOperator.AND) {
                return cb.and(predicates);
            } else {
                return cb.or(predicates);
            }
        }

        String field = query.getField();
        ConditionOperator op = query.getOp();
        Object value = query.getValue();

        Path<?> path = root.get(field);

        value = castValueToFieldType(root, field, value);
        return switch (op) {
            case EQUAL -> cb.equal(path, value);
            case NOT_EQUAL -> cb.notEqual(path, value);
            case GREATER_THAN -> cb.greaterThan((Expression<Comparable>) path, (Comparable) value);
            case GREATER_THAN_OR_EQUAL_TO -> cb.greaterThanOrEqualTo((Expression<Comparable>) path, (Comparable) value);
            case LESS_THAN -> cb.lessThan((Expression<Comparable>) path, (Comparable) value);
            case LESS_THAN_OR_EQUAL_TO -> cb.lessThanOrEqualTo((Expression<Comparable>) path, (Comparable) value);
            case LIKE -> cb.like(cb.lower(path.as(String.class)), "%" + value.toString().toLowerCase() + "%");
            case NOT_LIKE -> cb.notLike(cb.lower(path.as(String.class)), "%" + value.toString().toLowerCase() + "%");
            case IS_NULL -> cb.isNull(path);
            case IS_NOT_NULL -> cb.isNotNull(path);
            case TRUE -> cb.isTrue(path.as(Boolean.class));
            case FALSE -> cb.isFalse(path.as(Boolean.class));
            default -> null;
        };  
        // Add more operators as needed
    }

    private Object castValueToFieldType(Root<T> root, String field, Object value) {
        try{
            Class<?> fieldType = root.get(field).getJavaType();
            return objectMapper.convertValue(value, fieldType);
        } catch (Exception e) {
            return value;
        }
    }

}
