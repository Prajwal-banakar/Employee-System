package com.ems.payroll.controller;

import com.ems.payroll.pojo.Payroll;
import com.ems.payroll.service.PayrollService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PayrollControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PayrollService payrollService;

    @InjectMocks
    private PayrollController payrollController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(payrollController).build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules(); // To handle YearMonth
    }

    @Test
    public void testGeneratePayslip() throws Exception {
        Payroll payroll = new Payroll(
                UUID.randomUUID().toString(),
                "emp-123",
                YearMonth.of(2024, 3),
                new BigDecimal("5000"),
                new BigDecimal("1000"),
                new BigDecimal("500"),
                new BigDecimal("5500")
        );

        when(payrollService.savePayroll(any(Payroll.class))).thenReturn(payroll);

        mockMvc.perform(post("/api/payroll/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payroll)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value("emp-123"))
                .andExpect(jsonPath("$.netSalary").value(5500));
    }

    @Test
    public void testGetPayroll() throws Exception {
        Payroll payroll = new Payroll(
                UUID.randomUUID().toString(),
                "emp-123",
                YearMonth.of(2024, 3),
                new BigDecimal("5000"),
                new BigDecimal("1000"),
                new BigDecimal("500"),
                new BigDecimal("5500")
        );

        when(payrollService.getPayroll("emp-123")).thenReturn(Optional.of(payroll));

        mockMvc.perform(get("/api/payroll/emp-123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value("emp-123"));
    }

    @Test
    public void testGetPayroll_NotFound() throws Exception {
        when(payrollService.getPayroll("emp-404")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/payroll/emp-404"))
                .andExpect(status().isNotFound());
    }
}
