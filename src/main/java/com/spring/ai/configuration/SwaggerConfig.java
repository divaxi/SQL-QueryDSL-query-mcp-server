package com.spring.ai.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import lombok.extern.slf4j.Slf4j;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@Configuration
@Slf4j
public class SwaggerConfig {

    @Bean
    public OpenApiCustomizer queryDslExampleCustomizer() {
        return openApi -> {
            try {
                // Read the example JSON Request Object from file
                String json = Files.readString(Paths.get("src/main/resources/queryDSL-example.json"));
                Map<String, Object> exampleValue = new ObjectMapper().readValue(json, Map.class);

                // Fetch request body of the API
                if (openApi.getPaths().get("/employee/querydsl") != null) {
                    var post = openApi.getPaths().get("/employee/querydsl").getPost();
                    if (post.getRequestBody() != null) {
                        Content content = post.getRequestBody().getContent();
                        MediaType mediaType = content.get("application/json");
                        if (mediaType != null) {
                            Example example = new Example();
                            example.setValue(exampleValue);
                            mediaType.addExamples("Query example", example);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
