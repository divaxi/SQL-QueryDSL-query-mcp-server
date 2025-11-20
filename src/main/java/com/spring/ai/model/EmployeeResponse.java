package com.spring.ai.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.querydsl.core.annotations.QueryProjection;

public record EmployeeResponse(
        long id,
        String firstName,
        String lastName,
        String email,
        LocalDate hireDate,
        String managerFirstName,
        String managerLastName,
        String departmentName,
        Double salary,
        List<String> projectNames
) {

    @QueryProjection
    public EmployeeResponse(
            long id,
            String firstName,
            String lastName,
            String email,
            LocalDate hireDate,
            String managerFirstName,
            String managerLastName,
            String departmentName,
            Double salary,
            String projectNamesString
    ) {
        this(
            id,
            firstName,
            lastName,
            email,
            hireDate,
            managerFirstName,
            managerLastName,
            departmentName,
            salary,
            projectNamesString == null || projectNamesString.isBlank()
                ? List.of()
                : Arrays.stream(projectNamesString.split(","))
                        .map(String::trim)
                        .toList()
        );
    }
}

