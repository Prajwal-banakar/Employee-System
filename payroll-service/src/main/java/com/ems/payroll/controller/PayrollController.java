package com.ems.payroll.controller;

import com.ems.payroll.pojo.Payroll;
import com.ems.payroll.repo.PayrollRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/payroll")
public class PayrollController {

    @Autowired
    private PayrollRepo payrollRepo;

    @PostMapping("/")
    public Payroll savePayroll(@RequestBody Payroll payroll) {
        return payrollRepo.save(payroll);
    }

    @GetMapping("/{employeeId}")
    public Optional<Payroll> getPayroll(@PathVariable String employeeId) {
        return payrollRepo.findById(employeeId);
    }
}
