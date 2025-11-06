package com.spring.ai.model;

import java.io.Serializable;
import java.sql.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.ForeignKey;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
 class EmployeeProjectId implements Serializable {
    @Column(name = "EMPLOYEE_ID")
    private Long employeeId;

    @Column(name = "PROJECT_ID")
    private Long projectId;
}


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
    @JsonBackReference
    private Employee employee;

    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "PROJECT_ID", referencedColumnName = "PROJECT_ID",
     foreignKey = @ForeignKey(name = "FK_EP_PROJECT")
     )
    @JsonBackReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Project project;

}
