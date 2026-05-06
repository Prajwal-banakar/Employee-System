package com.ems.leave.service;

import com.ems.leave.model.Leave;
import com.ems.leave.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    public Leave applyForLeave(Leave leave) {
        return leaveRepository.save(leave);
    }

    public List<Leave> getLeavesByEmployeeId(String employeeId) {
        return leaveRepository.findByEmployeeId(employeeId);
    }

    public Optional<Leave> getLeaveById(String id) {
        return leaveRepository.findById(id);
    }

    public Leave updateLeave(Leave leave) {
        return leaveRepository.save(leave);
    }

}
