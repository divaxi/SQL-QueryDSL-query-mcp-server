---
resource: query://query-dsl
name: QueryDSL schema
description: >
  This document defines the JSON structure for sending QueryDSL-style search
  requests. The format is similar to Elasticsearch QueryDSL but simplified.
  A client must send a JSON body that matches the `QueryRequest` schema.
  All query logic must be expressed through `bool`, `range`, `match`, and `term`
  clauses. Each clause is a structured object with a predictable shape so that
  an AI assistant can reliably generate valid query JSON.

components:
  schemas:
    QueryRequest:
      type: object
      description: >
        The top-level request object. It contains a `query` field defining the
        search conditions and optional paging (`limit`, `page`).
    
        The `query` object must contain a `bool` clause at the top level.  
        Within a bool clause, arrays such as `must`, `should`, `mustNot`, and `filter`
        contain individual QueryClause objects (e.g., `match`, `range`, `bool`, `term`).
        
        The client should always construct the request exactly according to this structure.

      properties:
        query:
          type: object
          description: The root query container. Must contain a `bool` key that expresses the search logic.
          properties:
            bool:
              $ref: "#/components/schemas/BoolQuery"
        limit:
          type: integer
          format: int32
          description: Maximum number of results per page.
          default: 5
        page:
          type: integer
          format: int32
          description: Zero-based page index.
          default: 0
      required:
        - query
      example:
        query:
          bool:
            must:
              - range:
                  salary:
                    gte: 1500
                    lte: 2000
              - bool:
                  must:
                  should:
                    - match:
                        deparment_name: Engineering
                  mustNot:
                  filter:
            should:
            mustNot:
            filter:
        limit: 5
        page: 0
    QueryClause:
      type: object
      description: A single query clause inside bool array. Each QueryClause must contain **exactly one** of the following keys `range`, `match`, `term` and `bool`.
      properties:
        bool:
          $ref: "#/components/schemas/BoolQuery"
        range:
          $ref: "#/components/schemas/RangeQuery"
        match:
          $ref: "#/components/schemas/MatchQuery"
        term:
          $ref: "#/components/schemas/TermQuery"
      additionalProperties: false
    BoolQuery:
      type: object
      description: Boolean combination of clauses.
      properties:
        must:
          nullable: true
          description: >
             Boolean logic container.
             Each key (`must`, `should`, `mustNot`, `filter`) accepts an array of QueryClause. All keys are optional or nullable.
          type: array
          items:
            $ref: "#/components/schemas/QueryClause"
        should:
          nullable: true
          type: array
          items:
            $ref: "#/components/schemas/QueryClause"
        mustNot:
          nullable: true
          type: array
          items:
            $ref: "#/components/schemas/QueryClause"
        filter:
          nullable: true
          type: array
          items:
            $ref: "#/components/schemas/QueryClause"
    RangeQuery:
      type: object
      description: >
        Represents a range constraint on a numeric/date field.  
        The object is shaped as `fieldName -> RangeSpec`. 
      additionalProperties:
        $ref: "#/components/schemas/RangeSpec"
    RangeSpec:
      type: object
      description: Range boundaries for range query. Supports nummeric or string values (e.g., dates) 
      properties:
        gt:
          oneOf:
            - type: number
            - type: string
        gte:
          oneOf:
            - type: number
            - type: string
        lt:
          oneOf:
            - type: number
            - type: string
        lte:
          oneOf:
            - type: number
            - type: string
      example:
        range:
          salary:
            gte: 1500
            lte: 2000
    MatchQuery:
      type: object
      description: >
        Full-text match clause. The object maps a field name to a string.  
      additionalProperties:
        type: string
      example:
        deparment_name: Engineering
    TermQuery:
      type: object
      description: >
        Exact match clause. Maps a field name to a literal value (string/number/boolean). (string/number/bool).
      additionalProperties:
        oneOf:
          - type: string
          - type: number
          - type: boolean
      example:
        status: active
---
