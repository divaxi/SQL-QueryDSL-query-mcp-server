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
import org.springframework.stereotype.Service;

import com.spring.ai.dto.PagingList;
import com.spring.ai.dto.Employee.EmployeeResponse;
import com.spring.ai.dto.Query.QueryRequest;
import com.spring.ai.repository.EmployeeRepositoryQueryDsl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepositoryQueryDsl employeeRepositoryQueryDsl;

    @Tool(description = "Execute QueryDSL query to filtering Employee", name = "employees://execute-query")
    public PagingList<EmployeeResponse> filterEmployeeQueryDSL(QueryRequest queryRequest) {
        return employeeRepositoryQueryDsl.filterEmployeeQueryDsl(queryRequest);
    }

}