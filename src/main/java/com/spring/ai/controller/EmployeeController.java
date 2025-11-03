package com.spring.ai.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.ai.dto.EmployeePageRes;
import com.spring.ai.dto.QueryRequest;
import com.spring.ai.model.Employee;
import com.spring.ai.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    
    private final EmployeeService employeeService;

    @PostMapping()
    public ResponseEntity<EmployeePageRes> queryEmployees(@Valid @RequestBody QueryRequest<Employee> queryRequest) {
        return ResponseEntity.ok().body(
            employeeService.findAllFilteredEmployees(queryRequest)
        );
    }
}
