package com.ems.employeeservice.controller;

import com.ems.employeeservice.dto.EmployeeDto;
import com.ems.employeeservice.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void testCreateEmployee() throws Exception {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(UUID.randomUUID().toString());
        employeeDto.setEmployeeName("John Doe");
        when(employeeService.createEmployee(any(EmployeeDto.class))).thenReturn(employeeDto);

        mockMvc.perform(post("/api/employees/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"employeeName\":\"John Doe\",\"employeeEmail\":\"john.doe@example.com\",\"employeePhone\":1234567890,\"employeeGender\":\"Male\",\"employeeRole\":\"Developer\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.employeeName").value("John Doe"));
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        EmployeeDto employee1 = new EmployeeDto();
        employee1.setEmployeeName("John Doe");
        EmployeeDto employee2 = new EmployeeDto();
        employee2.setEmployeeName("Jane Doe");
        List<EmployeeDto> employees = Arrays.asList(employee1, employee2);
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<EmployeeDto> employeePage = new PageImpl<>(employees, pageRequest, employees.size());
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

    @Test
    public void testGetEmployeeSalary() throws Exception {
        when(restTemplate.getForObject(anyString(), any())).thenReturn("{\"employeeId\":\"1\",\"salary\":\"50000\"}");

        mockMvc.perform(get("/api/employees/1/salary"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"employeeId\":\"1\",\"salary\":\"50000\"}"));
    }
}
