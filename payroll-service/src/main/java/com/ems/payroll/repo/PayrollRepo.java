package com.ems.payroll.repo;

import com.ems.payroll.pojo.Payroll;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PayrollRepo extends MongoRepository<Payroll, String> {
}
