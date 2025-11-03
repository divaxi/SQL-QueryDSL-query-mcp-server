package com.spring.ai.dto;

import java.util.List;

import com.spring.ai.utils.ConditionOperator;

import jakarta.persistence.criteria.Predicate.BooleanOperator;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Query<T> {

    // logical operator (AND/OR)
    private BooleanOperator operator;

    // subconditions
    private List<Query<T>> conditions;

    // single condition
    private String field;
    private ConditionOperator op;
    private Object value;
}


