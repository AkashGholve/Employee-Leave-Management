package com.app.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "employee_masters")
public class Employee {
    @Id
    private Long id;
    private String name;
    private String department;
}
