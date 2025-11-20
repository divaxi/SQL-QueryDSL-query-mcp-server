package com.spring.ai.mapper;

import com.querydsl.core.types.Path;
import com.spring.ai.model.QEmployee;
import com.spring.ai.model.QEmployeeProject;
import com.spring.ai.model.QProject;

import java.util.Map;


public class EmployeeFieldMap extends AbstractFieldMap {

    private static final QEmployee qEmployee = QEmployee.employee;
    private static final QEmployeeProject qEmployeeProject = QEmployeeProject.employeeProject;
    private static final QProject qProject = QProject.project;

    public static final Map<String, Path<?>> FIELD_MAP = Map.of(
        "employee_id", qEmployee.employeeId,
        "first_name", qEmployee.firstName,
        "last_name", qEmployee.lastName,
        "salary", qEmployee.salary,
        "email", qEmployee.email,
        "hire_date", qEmployee.hireDate,
        "manager_first_name", qEmployee.manager.firstName,
        "manager_last_name", qEmployee.manager.lastName,
        "deparment_name", qEmployee.department.departmentName,
        "project_name", qProject.projectName
    );

    @Override
    protected Map<String,Path<?>> getFieldMap(){
        return FIELD_MAP;
    }

}
