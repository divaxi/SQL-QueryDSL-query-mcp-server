
package com.spring.ai.dto.Query;

import java.util.List;

import lombok.Data;

@Data
public class BoolQuery implements QueryClause {

    private Clause bool;

    @Data
    public static class Clause {

        private List<QueryClause> must;
        private List<QueryClause> should;
        private List<QueryClause> mustNot;
        private List<QueryClause> filter;
    }

    public void setMust(List<QueryClause> must) {
        if (this.bool == null) {
            this.bool = new Clause();
        }
        this.bool.setMust(must);
    }

    public void setShould(List<QueryClause> should) {
        if (this.bool == null) {
            this.bool = new Clause();
        }
        this.bool.setShould(should);
    }

    public void setMustNot(List<QueryClause> mustNot) {
        if (this.bool == null) {
            this.bool = new Clause();
        }
        this.bool.setMustNot(mustNot);
    }

    public void setFilter(List<QueryClause> filter) {
        if (this.bool == null) {
            this.bool = new Clause();
        }
        this.bool.setFilter(filter);
    }
}
