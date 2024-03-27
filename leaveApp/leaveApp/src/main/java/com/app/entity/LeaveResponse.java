package com.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeaveResponse {
    private Employee employee;
    private int workingDays;
    private int totalLeavesTaken;
    private int balanceLeaves;
}
