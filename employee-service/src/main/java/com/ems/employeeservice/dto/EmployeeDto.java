package com.ems.employeeservice.dto;

import lombok.Data;

@Data
public class EmployeeDto {
    private String id;
    private String employeeName;
    private String employeeEmail;
    private Long employeePhone;
    private String employeeGender;
    private String employeeRole;
}
