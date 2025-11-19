package com.spring.ai.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.ai.dto.QueryRequest;
import com.spring.ai.mapper.EmployeeFieldMap;
import com.spring.ai.model.Employee;
import com.spring.ai.model.EmployeeResponse;
import com.spring.ai.model.QEmployee;
import com.spring.ai.model.QEmployeeResponse;
import com.spring.ai.util.builder.QueryDSLBuilder;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class EmployeeRepositoryQueryDsl {

    private final JPAQueryFactory queryFactory;

    private final EmployeeFieldMap employeeFieldMap = new EmployeeFieldMap();

    private QueryDSLBuilder<Employee> queryDSLBuilder;

    @PostConstruct
    public void init() {
        this.queryDSLBuilder = new QueryDSLBuilder<>(employeeFieldMap);
    }

    public List<EmployeeResponse> filterEmployeeQueryDsl(QueryRequest queryRequest) {
        QEmployee employeeTable = QEmployee.employee;

        QEmployeeResponse employeeResponseTable = new QEmployeeResponse(
                employeeTable.employeeId,
                employeeTable.firstName,
                employeeTable.lastName,
                employeeTable.email,
                employeeTable.hireDate,
                employeeTable.manager.firstName,
                employeeTable.manager.lastName,
                employeeTable.department.departmentName,
                employeeTable.salary,
                Expressions.constant(""));

        Predicate queryPredicate = queryDSLBuilder.create(queryRequest, employeeTable);

        JPAQuery<EmployeeResponse> query = queryFactory
                .select(employeeResponseTable)
                .from(employeeTable)
                .leftJoin(employeeTable.manager)
                .leftJoin(employeeTable.department)
                .where(queryPredicate);

        List<EmployeeResponse> result = query.fetch();

        return result;

    }

}
