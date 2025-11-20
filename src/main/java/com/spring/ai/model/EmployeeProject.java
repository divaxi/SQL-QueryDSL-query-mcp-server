package com.spring.ai.model;

import java.sql.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.ForeignKey;

@Entity
@Data
@Getter
@Setter
@Table(name = "EMPLOYEE_PROJECTS")
@NoArgsConstructor
public class EmployeeProject {

    @EmbeddedId
    private EmployeeProjectId id;

    @Column(name= "ASSIGNED_DATE", nullable = false)
    private Date assignedDate;

    @Column(name = "ROLE", length = 50)
    private String role;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID",
     foreignKey = @ForeignKey(name = "FK_EP_EMPLOYEE")
     )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Employee employee;

    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "PROJECT_ID", referencedColumnName = "PROJECT_ID",
     foreignKey = @ForeignKey(name = "FK_EP_PROJECT")
     )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Project project;

}
