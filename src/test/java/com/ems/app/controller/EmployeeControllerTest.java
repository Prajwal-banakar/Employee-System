package com.ems.app.controller;

import com.ems.app.dto.EmployeeDto;
import com.ems.app.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    public void testCreateEmployee() throws Exception {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId("1");
        employeeDto.setEmployeeName("John Doe");
        when(employeeService.createEmployee(any(EmployeeDto.class))).thenReturn(employeeDto);

        mockMvc.perform(post("/api/employees/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"employeeName\":\"John Doe\",\"employeeEmail\":\"john.doe@example.com\",\"employeePhone\":1234567890,\"employeeGender\":\"Male\",\"employeeRole\":\"Developer\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.employeeName").value("John Doe"));
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        EmployeeDto employee1 = new EmployeeDto();
        employee1.setEmployeeName("John Doe");
        EmployeeDto employee2 = new EmployeeDto();
        employee2.setEmployeeName("Jane Doe");
        Page<EmployeeDto> employeePage = new PageImpl<>(Arrays.asList(employee1, employee2));
        when(employeeService.getAllEmployees(any(PageRequest.class))).thenReturn(employeePage);

        mockMvc.perform(get("/api/employees/viewall?page=0&size=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].employeeName").value("John Doe"))
                .andExpect(jsonPath("$.content[1].employeeName").value("Jane Doe"));
    }

    @Test
    public void testRemoveEmployee() throws Exception {
        when(employeeService.getEmployeeById("1")).thenReturn(Optional.of(new EmployeeDto()));

        mockMvc.perform(delete("/api/employees/remove/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee removed successfully."));
    }

    @Test
    public void testRemoveEmployeeNotFound() throws Exception {
        when(employeeService.getEmployeeById("1")).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/employees/remove/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Employee with ID 1 not found."));
    }
}
