package com.ems.app.service;

import com.ems.app.dto.EmployeeDto;
import com.ems.app.mapper.EmployeeMapper;
import com.ems.app.pojo.Employee;
import com.ems.app.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private EmployeeMapper employeeMapper;

    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.toEntity(employeeDto);
        String empId = "EMP" + (1000 + new Random().nextInt(9000));
        employee.setId(empId);
        return employeeMapper.toDto(employeeRepo.save(employee));
    }

    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.toEntity(employeeDto);
        return employeeMapper.toDto(employeeRepo.save(employee));
    }

    public void deleteEmployee(String id) {
        employeeRepo.deleteById(id);
    }

    public void deleteAllEmployees() {
        employeeRepo.deleteAll();
    }

    public Page<EmployeeDto> getAllEmployees(Pageable pageable) {
        return employeeRepo.findAll(pageable).map(employeeMapper::toDto);
    }

    public Optional<EmployeeDto> getEmployeeById(String id) {
        return employeeRepo.findById(id).map(employeeMapper::toDto);
    }
}
