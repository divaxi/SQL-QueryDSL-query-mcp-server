package com.spring.ai.dto.Query;

import java.util.Map;

import lombok.Data;

@Data
public class MatchQuery implements QueryClause {
    private Map<String, Object> match;
}
