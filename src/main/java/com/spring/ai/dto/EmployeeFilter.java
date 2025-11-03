package com.spring.ai.dto;

import java.time.LocalDate;

import org.springframework.ai.tool.annotation.ToolParam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeFilter {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate hireDate;
    private Long managerId;

    @ToolParam(description = "Job title of the employee. Must be one of: " +
            "'Human Resources Representative', 'Purchasing Manager', 'Accounting Manager', " +
            "'Stock Clerk', 'Accountant', 'Stock Manager', 'Sales Manager', 'Sales Representative', " +
            "'Administration Vice President', 'Shipping Clerk', 'Programmer', 'Finance Manager', " +
            "'Marketing Manager', 'Purchasing Clerk', 'Marketing Representative', 'Away'.")
    private JobTitle jobTitle;
    private Double salary;
}
