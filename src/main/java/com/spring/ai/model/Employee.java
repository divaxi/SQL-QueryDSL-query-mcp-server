package com.spring.ai.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "EMPLOYEES")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEE_ID", nullable = false)
    private Long employeeId;

    @Column(name = "FIRST_NAME", length = 50, nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", length = 50, nullable = false)
    private String lastName;

    @Column(name = "EMAIL", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "HIRE_DATE", nullable = false)
    private LocalDate hireDate;

    // ✅ Self-reference: Employee - Manager
    @ManyToOne
    @JoinColumn(name = "MANAGER_ID", referencedColumnName = "EMPLOYEE_ID",
            foreignKey = @ForeignKey(name = "FK_EMPLOYEE_MANAGER"))
    private Employee manager;

    // ✅ Quan hệ tới Department
    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID", referencedColumnName = "DEPARTMENT_ID",
            foreignKey = @ForeignKey(name = "FK_EMPLOYEE_DEPARTMENT"))
    private Department department;

    @Column(name = "SALARY")
    private Double salary;

    // ✅ Quan hệ ngược tới bảng EmployeeProject
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<EmployeeProject> employeeProjects = new ArrayList<>();
}

