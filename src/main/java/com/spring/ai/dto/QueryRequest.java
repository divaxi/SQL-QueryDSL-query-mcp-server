package com.spring.ai.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QueryRequest<T> {

    private  Query<T> where;

    @Builder.Default
    private Integer limit = 5;
    @Builder.Default
    private Integer page =1;
    
}
