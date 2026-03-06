package com.ems.employeeservice.controller;

import com.ems.employeeservice.dto.EmployeeDto;
import com.ems.employeeservice.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee Management", description = "APIs for managing employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/")
    @Operation(summary = "Welcome message", description = "Returns a welcome message.")
    public String homePage() {
        return "Welcome to the Employee Management System!";
    }

    @PostMapping("/create")
    @Operation(summary = "Create a new employee", description = "Creates a new employee with the provided details.")
    public ResponseEntity<EmployeeDto> newEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        EmployeeDto createdEmployee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @Operation(summary = "Update an existing employee", description = "Updates an existing employee's details.")
    public ResponseEntity<EmployeeDto> updateEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        EmployeeDto updatedEmployee = employeeService.updateEmployee(employeeDto);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    @DeleteMapping("/remove/{id}")
    @Operation(summary = "Remove an employee", description = "Removes an employee by their ID.")
    public ResponseEntity<String> removeEmployee(@PathVariable String id) {
        if (employeeService.getEmployeeById(id).isPresent()) {
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>("Employee removed successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Employee with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/removeall")
    @Operation(summary = "Remove all employees", description = "Removes all employees from the system.")
    public ResponseEntity<String> removeAll() {
        employeeService.deleteAllEmployees();
        return new ResponseEntity<>("All employees deleted successfully.", HttpStatus.OK);
    }

    @GetMapping("/viewall")
    @Operation(summary = "View all employees with pagination", description = "Returns a paginated list of all employees.")
    public ResponseEntity<Page<EmployeeDto>> viewAllEmployees(Pageable pageable) {
        Page<EmployeeDto> employees = employeeService.getAllEmployees(pageable);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{id}/salary")
    @Operation(summary = "Get employee salary", description = "Gets the salary of an employee from the payroll service.")
    public String getEmployeeSalary(@PathVariable String id) {
        return restTemplate.getForObject("http://localhost:8081/api/payroll/" + id, String.class);
    }
}
