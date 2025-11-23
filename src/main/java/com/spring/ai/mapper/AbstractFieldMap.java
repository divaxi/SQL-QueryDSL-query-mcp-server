package com.spring.ai.mapper;

import java.util.Map;

import com.querydsl.core.types.Path;


public abstract class AbstractFieldMap {

    protected  abstract  Map<String,Path<?>> getFieldMap();

    public Path<?> get(String fieldName) {
        Path<?> path = getFieldMap().get(fieldName);
        if (path == null) {
            throw new IllegalArgumentException("Unknown field name: " + fieldName);
        }
        return path;
    }
    
} 
