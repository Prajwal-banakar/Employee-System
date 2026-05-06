package com.ems.leave.controller;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveRequest {

    private String employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;

}
