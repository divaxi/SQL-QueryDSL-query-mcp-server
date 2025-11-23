package com.spring.ai.dto.Employee;

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
        List<ProjectObject> projects) {

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
            String projectDatasString) {
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
                projectDatasString == null || projectDatasString.isBlank()
                        ? List.of()
                        : Arrays.stream(projectDatasString.split(","))
                                .map(data -> {
                                    String[] parts = data.split("%", 2);

                                    String name = parts[0].trim();
                                    String role = parts.length == 2 ? parts[1].trim() : "";

                                    return new ProjectObject(name, role);
                                })
                                .toList());
    }
}

