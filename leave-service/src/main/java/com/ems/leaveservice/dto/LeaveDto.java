package com.ems.leaveservice.dto;

import lombok.Data;

@Data
public class LeaveDto {
    private String id;
    private String employeeId;
    private String leaveType;
    private String startDate;
    private String endDate;
    private String reason;
    private String status;
}
