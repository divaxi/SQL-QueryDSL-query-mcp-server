package com.spring.ai.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.ai.dto.EmployeePageRes;
import com.spring.ai.dto.PagingList;
import com.spring.ai.dto.QueryRequest;
import com.spring.ai.dto.Employee.EmployeeResponse;
import com.spring.ai.service.EmployeeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/employee")
@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/querydsl")
    public ResponseEntity<PagingList<EmployeeResponse>> queryDSLFilter(@Valid @RequestBody QueryRequest queryRequest){
        return ResponseEntity.ok(employeeService.filterEmployeeQueryDSL(queryRequest));
    }

}
