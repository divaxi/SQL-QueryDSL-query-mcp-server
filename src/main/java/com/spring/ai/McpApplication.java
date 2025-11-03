package com.spring.ai;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.spring.ai.service.EmployeeService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "MCP Server API",
		version = "1.0",
		description = "API documentation for MCP Server"
	)
)
public class McpApplication {

	public static void main(String[] args) {
		SpringApplication.run(McpApplication.class, args);
	}

	@Bean
	public ToolCallbackProvider employeeTools(EmployeeService employeeService) {
		return MethodToolCallbackProvider.builder().toolObjects(employeeService).build();
	}

}
