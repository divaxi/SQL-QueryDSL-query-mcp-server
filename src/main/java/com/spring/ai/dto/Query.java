package com.spring.ai.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Query<T> {

    // logical operator (AND/OR)
    private LogicalOperator operator;

    // subconditions
    private List<Query<T>> conditions;

    // single condition
    private String field;
    private ConditionOperator op;
    private Object value;

    
}


enum LogicalOperator {
    AND,
    OR
}

 enum ConditionOperator {

    // 🟢 So sánh cơ bản (Comparisons)
    EQUAL,              // cb.equal(x, y)
    NOT_EQUAL,          // cb.notEqual(x, y)
    GREATER_THAN,       // cb.greaterThan(x, y)
    GREATER_THAN_OR_EQUAL_TO, // cb.greaterThanOrEqualTo(x, y)
    LESS_THAN,          // cb.lessThan(x, y)
    LESS_THAN_OR_EQUAL_TO, // cb.lessThanOrEqualTo(x, y)

    // 🟢 So sánh chuỗi (String pattern)
    LIKE,               // cb.like(x, pattern)
    NOT_LIKE,           // cb.notLike(x, pattern)

    // 🟢 Null check
    IS_NULL,            // cb.isNull(x)
    IS_NOT_NULL,        // cb.isNotNull(x)

    // 🟢 Tập hợp (Collections)
    IN,                 // x.in(list)
    NOT_IN,             // cb.not(x.in(list))

    // 🟢 Khoảng (Range)
    BETWEEN,            // cb.between(x, lower, upper)
    NOT_BETWEEN,        // cb.not(cb.between(x, lower, upper))

    // 🟢 Boolean (logical)
    TRUE,               // cb.isTrue(x)
    FALSE              // cb.isFalse(x)

}
