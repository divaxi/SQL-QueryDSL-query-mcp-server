package com.spring.ai.util.deserializer;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.spring.ai.dto.Query.BoolQuery;
import com.spring.ai.dto.Query.MatchQuery;
import com.spring.ai.dto.Query.QueryClause;
import com.spring.ai.dto.Query.RangeQuery;
import com.spring.ai.dto.Query.TermQuery;

public class QueryDeserializer extends JsonDeserializer<QueryClause> {

    @Override
    @SuppressWarnings("unchecked")
    public QueryClause deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        ObjectNode node = mapper.readTree(p);
        Iterator<String> fieldNames = node.fieldNames();
        if (!fieldNames.hasNext()) {
            throw new IllegalArgumentException("Empty query node");
        }

        String type = fieldNames.next();
        JsonNode content = node.get(type);

        switch (type) {
            case "match":
                MatchQuery matchQuery = new MatchQuery();
                matchQuery.setMatch(mapper.convertValue(content, Map.class));
                return matchQuery;
            case "term":
                TermQuery termQuery = new TermQuery();
                termQuery.setTerm(mapper.convertValue(content, Map.class));
                return termQuery;
            case "range":
                RangeQuery rangeQuery = new RangeQuery();
                rangeQuery.setRange(mapper.convertValue(content, new TypeReference<Map<String,RangeQuery.RangeCondition>>() {}));
                return rangeQuery;
            case "bool":
                BoolQuery boolQuery = new BoolQuery();
                if (content.has("must"))
                    boolQuery.setMust(mapper.convertValue(content.get("must"),
                            mapper.getTypeFactory().constructCollectionType(
                                    java.util.List.class, QueryClause.class)));
                if (content.has("should"))
                    boolQuery.setShould(mapper.convertValue(content.get("should"),
                            mapper.getTypeFactory().constructCollectionType(
                                    java.util.List.class, QueryClause.class)));
                if (content.has("must_not"))
                    boolQuery.setMustNot(mapper.convertValue(content.get("must_not"),
                            mapper.getTypeFactory().constructCollectionType(
                                    java.util.List.class, QueryClause.class)));
                if (content.has("filter"))
                    boolQuery.setFilter(mapper.convertValue(content.get("filter"),
                            mapper.getTypeFactory().constructCollectionType(
                                    java.util.List.class, QueryClause.class)));
                return boolQuery;
            default:
                throw new IllegalArgumentException("Unknown query type: " + type);
        }
    }
}
