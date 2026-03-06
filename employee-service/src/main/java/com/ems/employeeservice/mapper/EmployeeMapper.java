package com.ems.employeeservice.mapper;

import com.ems.employeeservice.dto.EmployeeDto;
import com.ems.employeeservice.pojo.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeDto toDto(Employee employee) {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setEmployeeName(employee.getEmployeeName());
        dto.setEmployeeEmail(employee.getEmployeeEmail());
        dto.setEmployeePhone(employee.getEmployeePhone());
        dto.setEmployeeGender(employee.getEmployeeGender());
        dto.setEmployeeRole(employee.getEmployeeRole());
        return dto;
    }

    public Employee toEntity(EmployeeDto dto) {
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setEmployeeName(dto.getEmployeeName());
        employee.setEmployeeEmail(dto.getEmployeeEmail());
        employee.setEmployeePhone(dto.getEmployeePhone());
        employee.setEmployeeGender(dto.getEmployeeGender());
        employee.setEmployeeRole(dto.getEmployeeRole());
        return employee;
    }
}
