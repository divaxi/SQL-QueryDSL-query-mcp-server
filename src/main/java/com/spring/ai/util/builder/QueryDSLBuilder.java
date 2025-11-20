package com.spring.ai.util.builder;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.spring.ai.dto.BoolQuery;
import com.spring.ai.dto.MatchQuery;
import com.spring.ai.dto.QueryClause;
import com.spring.ai.dto.QueryRequest;
import com.spring.ai.dto.RangeQuery;
import com.spring.ai.dto.TermQuery;
import com.spring.ai.mapper.AbstractFieldMap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@SuppressWarnings({"unchecked","rawtypes"})
public class QueryDSLBuilder {

    private final AbstractFieldMap fieldMap;

    public  Predicate create(QueryRequest queryDslObj, EntityPathBase table) {
        BoolQuery queryJson = queryDslObj.getQuery();
        return buildPredicate(queryJson, table);
    }

    public  Predicate buildPredicate(BoolQuery queryJson, EntityPathBase table)
            throws IllegalArgumentException {
        BooleanBuilder builder = new BooleanBuilder();
        BoolQuery.Clause clauses = queryJson.getBool();

        // Process each clause and add to builder
        if (clauses.getMust() != null && !clauses.getMust().isEmpty()) {
            for (QueryClause clause : clauses.getMust()) {
                builder.and(parseClause(clause, table));
            }
        }
        if (clauses.getShould() != null && !clauses.getShould().isEmpty()) {
            for (QueryClause clause : clauses.getShould()) {
                builder.or(parseClause(clause, table));
                // Process each clause and add to builder
            }
        }

        if (clauses.getMustNot() != null && !clauses.getMustNot().isEmpty()) {
            for (QueryClause clause : clauses.getMustNot()) {
                builder.andNot(parseClause(clause, table));
                // Process each clause and add to builder
            }
        }

        if (clauses.getFilter() != null && !clauses.getFilter().isEmpty()) {
            for (QueryClause clause : clauses.getFilter()) {
                builder.and(parseClause(clause, table));
                // Process each clause and add to builder
            }

        }

        return builder;
    }

    private Predicate parseClause(QueryClause clause, EntityPathBase table) {
        if (clause == null)
            return null;

        if (clause instanceof BoolQuery boolQuery) {
            return buildPredicate(boolQuery, table);
        } else if (clause instanceof MatchQuery match) {
            return parseMatch(match, table);
        } else if (clause instanceof TermQuery term) {
            return parseTerm(term, table);
        } else if (clause instanceof RangeQuery range) {
            return parseRange(range, table);
        } else {
            throw new IllegalArgumentException("Unknown query clause type: " + clause.getClass().getName());
        }

    }

    private  Predicate parseMatch(MatchQuery match, EntityPathBase table) {
        BooleanBuilder builder = new BooleanBuilder();

        // Lấy field và value từ map
        if (match.getMatch() == null || match.getMatch().isEmpty()) {
            throw new IllegalArgumentException("MatchQuery cannot be empty");
        }

        Map.Entry<String, Object> entry = match.getMatch().entrySet().iterator().next();

        String field = entry.getKey();
        Object value = entry.getValue();

        // Lấy Path<> tương ứng từ FieldMap
        Path<?> path = fieldMap.get(field);

        // Tạo predicate dựa vào loại Path
        if (path instanceof StringPath stringPath) {
            builder.and(stringPath.containsIgnoreCase(value.toString()));
        } else if (path instanceof NumberPath<?> numberPath) {
            Object typedValue = convertToNumberType(value, numberPath.getType());
            builder.and(((NumberPath) numberPath).eq(typedValue));
        } else if (path instanceof BooleanPath booleanPath) {
            builder.and(booleanPath.eq(Boolean.parseBoolean(value.toString())));
        } else if (path instanceof DatePath<?> datePath) {
            Object typedValue = convertToDateType(value, datePath.getType());
            builder.and(((DatePath) datePath).eq(typedValue));
        } else {
            throw new IllegalArgumentException("Unsupported field type: " + path.getClass());
        }

        return builder;
    }

    private Predicate parseTerm(TermQuery termQuery, EntityPathBase table) {
        BooleanBuilder builder = new BooleanBuilder();

        // Validate
        if (termQuery.getTerm() == null || termQuery.getTerm().isEmpty()) {
            throw new IllegalArgumentException("TermQuery cannot be empty");
        }

        // Lấy field và value
        Map.Entry<String, Object> entry = termQuery.getTerm().entrySet().iterator().next();
        String field = entry.getKey();
        Object value = entry.getValue();

        // Map field -> Q-class
        Path<?> path = fieldMap.get(field);

        // Build predicate
        if (path instanceof StringPath stringPath) {
            builder.and(stringPath.eq(value.toString()));
        } else if (path instanceof NumberPath<?> numberPath) {
            Object typedValue = convertToNumberType(value, numberPath.getType());
            builder.and(((NumberPath) numberPath).eq(typedValue));
        } else if (path instanceof BooleanPath booleanPath) {
            builder.and(booleanPath.eq(Boolean.parseBoolean(value.toString())));
        } else if (path instanceof DatePath<?> datePath) {
            Comparable<?> typedValue = convertToDateType(value, datePath.getType());
            builder.and(((DatePath) datePath).eq(typedValue));
        } else {
            throw new IllegalArgumentException("Unsupported field type for term query: " + path.getClass());
        }

        return builder;
    }

    private  Predicate parseRange(RangeQuery rangeQuery, EntityPathBase table) {
        BooleanBuilder builder = new BooleanBuilder();

        if (rangeQuery.getRange() == null || rangeQuery.getRange().isEmpty()) {
            throw new IllegalArgumentException("RangeQuery cannot be empty");
        }

        Map.Entry<String, RangeQuery.RangeCondition> entry = rangeQuery.getRange().entrySet().iterator().next();


        String field = entry.getKey();

        RangeQuery.RangeCondition condition = entry.getValue();

        Path<?> path = fieldMap.get(field);
        if (path == null) {
            throw new IllegalArgumentException("Field not found in FieldMap: " + field);
        }

        // --- VALIDATE ---
        if (path instanceof StringPath) {
            throw new IllegalArgumentException("Range query is not applicable for String fields: " + field);
        }

        // ===============================
        // NUMBER RANGE
        // ===============================

        if (path instanceof NumberPath<?> nP) {

            NumberPath numberPath = ((NumberPath)nP);


            if (condition.getGte() != null) {
                Number typedValue =(Number) convertToNumberType(condition.getGte(), numberPath.getType());
                builder.and(numberPath.goe(typedValue));
            }

            if (condition.getGt() != null) {
                Number typedValue =(Number) convertToNumberType(condition.getGt(), numberPath.getType());
                builder.and(numberPath.gt(typedValue));
            }

            if (condition.getLte() != null) {
                Number typedValue = (Number) convertToNumberType(condition.getLte(), numberPath.getType());
                builder.and(numberPath.loe(typedValue));
            }

            if (condition.getLt() != null) {
                Number typedValue = (Number) convertToNumberType(condition.getLt(), numberPath.getType());
                builder.and(numberPath.lt(typedValue));
            }

            return builder;
        }

        // ===============================
        // DATE RANGE
        // ===============================
        if (path instanceof DatePath<?> dP) {

            DatePath<Comparable<?>> datePath = ((DatePath<Comparable<?>>)dP);

            if (condition.getGte() != null) {
                Comparable<?> typedValue =convertToDateType(condition.getGte(), datePath.getType());
                builder.and(datePath.goe(typedValue));
            }

            if (condition.getGt() != null) {
                Comparable<?> v = convertToDateType(condition.getGt(), datePath.getType());
                builder.and(datePath.gt(v));
            }

            if (condition.getLte() != null) {
                Comparable<?> v = convertToDateType(condition.getLte(), datePath.getType());
                builder.and(datePath.loe(v));
            }

            if (condition.getLt() != null) {
                Comparable<?> v = convertToDateType(condition.getLt(), datePath.getType());
                builder.and(datePath.lt(v));
            }

            return builder;
        }

        throw new IllegalArgumentException("Unsupported range field type: " + path.getClass());
    }

    private Object convertToNumberType(Object value, Class<?> type) {
        if (value == null)
            return null;

        String s = value.toString();

        if (type == Integer.class)
            return Integer.parseInt(s);
        if (type == Long.class)
            return Long.parseLong(s);
        if (type == Double.class)
            return Double.parseDouble(s);
        if (type == Float.class)
            return Float.parseFloat(s);
        if (type == Short.class)
            return Short.parseShort(s);
        if (type == Byte.class)
            return Byte.parseByte(s);

        // fallback
        if (Number.class.isAssignableFrom(type)) {
            return Integer.parseInt(s);
        }

        throw new IllegalArgumentException("Unsupported numeric type: " + type);
    }

private Comparable<?> convertToDateType(Object value, Class<?> type) {
    String s = value.toString();
    if (type == LocalDate.class) return LocalDate.parse(s);
    if (type == LocalDateTime.class) return LocalDateTime.parse(s);
    if (type == Instant.class) return Instant.parse(s);
    throw new IllegalArgumentException("Unsupported date type: " + type);
}


}
