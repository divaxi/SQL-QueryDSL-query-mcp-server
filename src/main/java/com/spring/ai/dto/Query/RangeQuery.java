package com.spring.ai.dto.Query;

import java.util.Map;

import lombok.Data;

@Data
public class RangeQuery implements QueryClause {
    private Map<String, RangeCondition> range;
    
    @Data
    public static class RangeCondition {
        private Object gte;
        private Object lte;
        private Object gt;
        private Object lt;
    }
}
