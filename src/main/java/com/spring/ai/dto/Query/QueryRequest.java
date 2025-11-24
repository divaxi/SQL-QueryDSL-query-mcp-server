package com.spring.ai.dto.Query;

import lombok.Data;

@Data
public class QueryRequest {

    private BoolQuery query;

    private int limit = 5;

    private int page = 0;

}
