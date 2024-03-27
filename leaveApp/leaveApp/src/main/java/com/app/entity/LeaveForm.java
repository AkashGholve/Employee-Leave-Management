package com.app.entity;

import java.time.LocalDate;

import lombok.Data;

@Data
public class LeaveForm {
    private Long employeeId;
    private String fromDate;
    private String toDate;
}
