package com.ems.leave.controller;

import com.ems.leave.model.Leave;
import com.ems.leave.model.LeaveStatus;
import com.ems.leave.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaves")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @PostMapping
    public Leave applyForLeave(@RequestBody LeaveRequest leaveRequest) {
        Leave leave = new Leave();
        leave.setEmployeeId(leaveRequest.getEmployeeId());
        leave.setStartDate(leaveRequest.getStartDate());
        leave.setEndDate(leaveRequest.getEndDate());
        leave.setReason(leaveRequest.getReason());
        return leaveService.applyForLeave(leave);
    }

    @GetMapping("/employee/{employeeId}")
    public List<Leave> getLeavesByEmployeeId(@PathVariable String employeeId) {
        return leaveService.getLeavesByEmployeeId(employeeId);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<Leave> approveLeave(@PathVariable String id) {
        return leaveService.getLeaveById(id)
                .map(leave -> {
                    leave.setStatus(LeaveStatus.APPROVED);
                    return ResponseEntity.ok(leaveService.updateLeave(leave));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<Leave> rejectLeave(@PathVariable String id) {
        return leaveService.getLeaveById(id)
                .map(leave -> {
                    leave.setStatus(LeaveStatus.REJECTED);
                    return ResponseEntity.ok(leaveService.updateLeave(leave));
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
