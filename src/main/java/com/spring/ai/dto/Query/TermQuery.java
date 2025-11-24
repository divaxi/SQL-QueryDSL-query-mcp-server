package com.spring.ai.dto.Query;

import java.util.Map;

import lombok.Data;

@Data
public class TermQuery implements QueryClause {

    private Map<String, Object> term;
}
