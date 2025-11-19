package com.spring.ai.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.ai.dto.EmployeePageRes;
import com.spring.ai.dto.QueryRequest;
import com.spring.ai.model.EmployeeResponse;
import com.spring.ai.service.EmployeeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/employee")
@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("")
    public ResponseEntity<EmployeePageRes> findAll(){
        return ResponseEntity.ok(employeeService.findAllFilteredEmployees(null));
    }

    @PostMapping("/test")
    public ResponseEntity<QueryRequest> testEndpoint(@Valid @RequestBody QueryRequest queryRequest){
        return ResponseEntity.ok(queryRequest);
    }

    @PostMapping("/querydsl")
    public ResponseEntity<List<EmployeeResponse>> queryDSLFilter(@Valid @RequestBody QueryRequest queryRequest){
        return ResponseEntity.ok(employeeService.filterEmployeeQueryDSL(queryRequest));
    }
    
}
