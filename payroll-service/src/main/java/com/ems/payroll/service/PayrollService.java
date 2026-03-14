package com.ems.payroll.service;

import com.ems.payroll.pojo.Payroll;
import com.ems.payroll.repo.PayrollRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PayrollService {

    @Autowired
    private PayrollRepo payrollRepo;

    public Payroll savePayroll(Payroll payroll) {
        // In a real scenario, you would calculate deductions, allowances, etc.
        // For now, we'll just save the provided data.
        return payrollRepo.save(payroll);
    }

    public Optional<Payroll> getPayroll(String employeeId) {
        return payrollRepo.findById(employeeId);
    }
}
