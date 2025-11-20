package com.spring.ai.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PagingList<T> {

    private List<T> data;
    private long total;
    private long limit;
    private long page;
    
}
