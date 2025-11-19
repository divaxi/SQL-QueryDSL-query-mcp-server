/*
 * Copyright 2024 - 2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.spring.ai.service;



import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring.ai.dto.EmployeeFilter;
import com.spring.ai.dto.EmployeePageRes;
import com.spring.ai.dto.QueryRequest;
import com.spring.ai.model.Employee;
import com.spring.ai.model.EmployeeResponse;
import com.spring.ai.repository.EmployeeRepository;
import com.spring.ai.repository.EmployeeRepositoryQueryDsl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

private final EmployeeRepository employeeRepository;

private final EmployeeRepositoryQueryDsl employeeRepositoryQueryDsl;

    @Tool(description = "Find Page of employees with filtering options")
    public EmployeePageRes findAllFilteredEmployees(@ToolParam(description = "Filtering options for employee search") EmployeeFilter option) {

    Pageable pageable = PageRequest.of(0, 2);

    log.info("Finding all filtered employees with options: {}", option);

    Page<Employee> employeePage = employeeRepository.findAll( pageable);

    return EmployeePageRes.builder()
            .employees(employeePage.getContent())
            .totalPages(employeePage.getTotalPages())
            .totalElements(employeePage.getTotalElements())
            .page(employeePage.getNumber())
            .limit(employeePage.getSize())
            .build();
}

@Tool(description ="QueryDSL for filtering Employee")
public List<EmployeeResponse> filterEmployeeQueryDSL( QueryRequest queryRequest) {     
    return employeeRepositoryQueryDsl.filterEmployeeQueryDsl(queryRequest);
}

@Tool(description ="Find Employee by ID")
public Employee findEmployeeById( @ToolParam(description="Employee ID") Long id) {     
    return employeeRepository.findById(id).orElse(null);
}


}