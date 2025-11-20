package com.spring.ai.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.ai.dto.PagingList;
import com.spring.ai.dto.QueryRequest;
import com.spring.ai.mapper.EmployeeFieldMap;
import com.spring.ai.model.Employee;
import com.spring.ai.model.EmployeeResponse;
import com.spring.ai.model.QEmployee;
import com.spring.ai.model.QEmployeeProject;
import com.spring.ai.model.QEmployeeResponse;
import com.spring.ai.model.QProject;
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
private QueryDSLBuilder queryDSLBuilder;  

/**  
 * Initialize the QueryDSLBuilder after the bean is constructed.  
 */  
@PostConstruct  
public void init() {  
    this.queryDSLBuilder = new QueryDSLBuilder(employeeFieldMap);  
}  

/**  
 * Filter employees using dynamic QueryDSL predicates and return a paginated result.  
 * Uses string aggregation to combine project names into a single column.  
 *  
 * @param queryRequest The dynamic query request containing filters, pagination info.  
 * @return PagingList of EmployeeResponse with aggregated project names.  
 */  
public PagingList<EmployeeResponse> filterEmployeeQueryDsl(QueryRequest queryRequest) {  
    // QueryDSL Q-types for entities  
    QEmployee employeeTable = QEmployee.employee;  
    QEmployeeProject employeeProject = QEmployeeProject.employeeProject;  
    QProject projectTable = QProject.project;  

    // Construct the projection DTO with string aggregation for projects  
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
            Expressions.stringTemplate("string_agg({0}, ',')", projectTable.projectName) // Aggregate projects into a single string  
    );  

    // Build the dynamic predicate based on QueryRequest  
    Predicate queryPredicate = queryDSLBuilder.create(queryRequest, employeeTable);  

    // Build the main query  
    JPAQuery<EmployeeResponse> query = queryFactory  
            .select(employeeResponseTable)  
            .from(employeeTable)  
            .leftJoin(employeeTable.manager)  
            .leftJoin(employeeTable.department)  
            .leftJoin(employeeTable.projects, employeeProject)  
            .leftJoin(employeeProject.project, projectTable)  
            .where(queryPredicate)  
            // GROUP BY required for aggregate function string_agg  
            .groupBy(  
                    employeeTable.employeeId,  
                    employeeTable.firstName,  
                    employeeTable.lastName,  
                    employeeTable.email,  
                    employeeTable.hireDate,  
                    employeeTable.manager.firstName,  
                    employeeTable.manager.lastName,  
                    employeeTable.department.departmentName,  
                    employeeTable.salary  
            )  
            // Pagination using offset and limit  
            .offset(queryRequest.getPage() * queryRequest.getLimit())  
            .limit(queryRequest.getLimit());  

    // Execute query and fetch results  
    List<EmployeeResponse> result = query.fetch();  

    // Return paginated result including total count for paging  
    return new PagingList<>(  
            result,  
            countQuery(queryPredicate).fetchOne(), // Total count matching the predicate  
            queryRequest.getLimit(),  
            queryRequest.getPage()  
    );  
}  

/**  
 * Count the total number of employees matching the predicate for pagination.  
 *  
 * @param queryPredicate The dynamic filter predicate  
 * @return JPAQuery<Long> representing the count query  
 */  
private JPAQuery<Long> countQuery(Predicate queryPredicate) {  
    QEmployee employeeTable = QEmployee.employee;  

    return queryFactory  
            .select(employeeTable.count())  
            .from(employeeTable)  
            .leftJoin(employeeTable.manager)  
            .leftJoin(employeeTable.department)  
            .where(queryPredicate);  
}  

}

