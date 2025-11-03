package com.spring.ai.utils;

public enum ConditionOperator {

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

    // 🟢 Boolean (logical)
    TRUE,               // cb.isTrue(x)
    FALSE              // cb.isFalse(x)

}
