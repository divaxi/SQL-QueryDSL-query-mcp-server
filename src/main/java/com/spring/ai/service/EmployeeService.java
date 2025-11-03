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



import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.spring.ai.dto.EmployeeFilter;
import com.spring.ai.dto.EmployeePageRes;
import com.spring.ai.model.Employee;
import com.spring.ai.model.EmployeeSpecification;
import com.spring.ai.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

private final EmployeeRepository employeeRepository;

    @Tool(description = "Find Page of employees with filtering options")
    public EmployeePageRes findAllFilteredEmployees(@ToolParam(description = "Filtering options for employee search") EmployeeFilter option) {

    Pageable pageable = PageRequest.of(option.getPage(), option.getLimit());

    log.info("Finding all filtered employees with options: {}", option);
    
    Specification<Employee> spec = Specification.where(null);


    if (option.getEmployeeId() != null) {
        spec = spec.and(EmployeeSpecification.hasEmployeeId(option.getEmployeeId()));
    }

    if (option.getFirstName() != null) {
        spec = spec.and(EmployeeSpecification.hasFirstName(option.getFirstName()));
    }

    if (option.getLastName() != null) {
        spec = spec.and(EmployeeSpecification.hasLastName(option.getLastName())); 
    }
    if (option.getEmail() != null) {
        spec = spec.and(EmployeeSpecification.hasEmail(option.getEmail()));
    }
    if (option.getPhone() != null) {
        spec = spec.and(EmployeeSpecification.hasPhone(option.getPhone()));
    }
    if (option.getHireDateFrom() != null || option.getHireDateTo() != null) {
        spec = spec.and(EmployeeSpecification.hireDateBetween(option.getHireDateFrom(), option.getHireDateTo()));
    }   
    if (option.getManagerId() != null) {
        spec = spec.and(EmployeeSpecification.hasManagerId(option.getManagerId()));
    }   
    if (option.getJobTitle() != null) { 
        spec = spec.and(EmployeeSpecification.hasJobTitle(option.getJobTitle()));
    }   
    if (option.getMinSalary() != null || option.getMaxSalary() != null) {
        spec = spec.and(EmployeeSpecification.salaryBetween(option.getMinSalary(), option.getMaxSalary()));
    } 
    Page<Employee> employeePage = employeeRepository.findAll(spec, pageable);

    return EmployeePageRes.builder()
            .employees(employeePage.getContent())
            .totalPages(employeePage.getTotalPages())
            .totalElements(employeePage.getTotalElements())
            .page(employeePage.getNumber())
            .limit(employeePage.getSize())
            .build();
}

@Tool(description ="Find Employee by ID")
public Employee findEmployeeById( @ToolParam(description="Employee ID") Long id) {     
    return employeeRepository.findById(id).orElse(null);
}

@Tool(description ="Test tool method")
public String testToolMethod( @ToolParam(description="A test parameter") EmployeeFilter option) {
    return "Test tool method executed successfully with parameter: " + option;
}

}