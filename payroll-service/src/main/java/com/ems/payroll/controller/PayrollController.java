package com.ems.payroll.controller;

import com.ems.payroll.pojo.Payroll;
import com.ems.payroll.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/payroll")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;

    @PostMapping("/")
    public ResponseEntity<Payroll> generatePayslip(@RequestBody Payroll payroll) {
        // Here you could add logic to calculate net salary from basic, allowances, and deductions
        Payroll savedPayroll = payrollService.savePayroll(payroll);
        return ResponseEntity.ok(savedPayroll);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Payroll> getPayroll(@PathVariable String employeeId) {
        Optional<Payroll> payroll = payrollService.getPayroll(employeeId);
        return payroll.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
}
