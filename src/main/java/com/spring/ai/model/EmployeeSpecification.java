package com.spring.ai.model;

import org.springframework.data.jpa.domain.Specification;

import com.spring.ai.dto.JobTitle;

public class EmployeeSpecification {

    public static Specification<Employee> salaryBetween(Double minSalary, Double maxSalary) {
        return (root, query, cb) -> {
            if (minSalary == null && maxSalary == null) {
                return null;
            } else if (minSalary == null) {
                return cb.lessThanOrEqualTo(root.get(Employee_.salary), maxSalary);
            } else if (maxSalary == null) {
                return cb.greaterThanOrEqualTo(root.get(Employee_.salary), minSalary);
            } else {
                return cb.between(root.get(Employee_.salary), minSalary, maxSalary);
            }
        };
    }
    public static Specification<Employee> hireDateBetween(java.time.LocalDate from, java.time.LocalDate to) {

        return (root, query, cb) -> {
            if (from == null && to == null) {
                return null;
            } else if (from == null) {
                return cb.lessThanOrEqualTo(root.get(Employee_.hireDate), to);
            } else if (to == null) {
                return cb.greaterThanOrEqualTo(root.get(Employee_.hireDate), from);
            } else {
                return cb.between(root.get(Employee_.hireDate), from, to);
            }
        };
    }
    public static Specification<Employee> hasManagerId(Long managerId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root
                        .get(Employee_.managerId)
                         ,managerId);
    }   
    public static Specification<Employee> hasJobTitle(JobTitle jobTitle) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(Employee_.jobTitle))
                ,"%" + jobTitle.name().toLowerCase() + "%");
    }   
    public static Specification<Employee> hasEmail(String email) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root
                        .get(Employee_.email)
                         ,email);
    }
    public static Specification<Employee> hasPhone(String phone) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root
                        .get(Employee_.phone)
                         ,phone);
    }
    public static Specification<Employee> hasFirstName(String firstName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root
                        .get(Employee_.firstName)
                         ,"%" + firstName + "%");
    }

    public static Specification<Employee> hasLastName(String lastName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root
                        .get(Employee_.lastName)
                         ,"%" + lastName + "%");
    }   
    public static Specification<Employee> hasEmployeeId(long id) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root
                        .get(Employee_.employeeId)
                         ,id);
    }


}