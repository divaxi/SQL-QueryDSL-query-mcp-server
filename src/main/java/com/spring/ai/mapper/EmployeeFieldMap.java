package com.spring.ai.mapper;

import java.util.Map;

import com.querydsl.core.types.Path;
import com.spring.ai.model.QEmployee;
import com.spring.ai.model.QEmployeeProject;
import com.spring.ai.model.QProject;


public class EmployeeFieldMap extends AbstractFieldMap {


    public final Map<String, Path<?>> FIELD_MAP;


    public EmployeeFieldMap(QEmployee qEmployee,
                            QEmployeeProject qEmployeeProject,
                            QProject qProject) {
        this.FIELD_MAP = Map.ofEntries(
        Map.entry("employee_id", qEmployee.employeeId),
        Map.entry("first_name", qEmployee.firstName),
        Map.entry("last_name", qEmployee.lastName),
        Map.entry("salary", qEmployee.salary),
        Map.entry("email", qEmployee.email),
        Map.entry("hire_date", qEmployee.hireDate),
        Map.entry("manager_first_name", qEmployee.manager.firstName),
        Map.entry("manager_last_name", qEmployee.manager.lastName),
        Map.entry("deparment_name", qEmployee.department.departmentName),
        Map.entry("project_name", qProject.projectName),
        Map.entry("project_role", qEmployeeProject.role)
);

    }

    @Override
    protected Map<String,Path<?>> getFieldMap() {
        return FIELD_MAP;
    }
}

