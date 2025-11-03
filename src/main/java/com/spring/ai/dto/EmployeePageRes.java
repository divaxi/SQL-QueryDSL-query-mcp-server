package com.spring.ai.dto;

import java.util.List;

import com.spring.ai.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePageRes {

    private List<Employee> employees;
    private int totalPages;
    private long totalElements;
    private int page;
    private int limit;
}
