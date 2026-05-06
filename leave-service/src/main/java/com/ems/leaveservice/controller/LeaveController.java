package com.ems.leaveservice.controller;

import com.ems.leaveservice.dto.LeaveDto;
import com.ems.leaveservice.service.LeaveService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping("/apply")
    public ResponseEntity<LeaveDto> applyLeave(@RequestBody LeaveDto leaveDto) {
        LeaveDto createdLeave = leaveService.applyLeave(leaveDto);
        return new ResponseEntity<>(createdLeave, HttpStatus.CREATED);
    }

    @GetMapping("/viewall")
    public Page<LeaveDto> getAllLeaves(Pageable pageable) {
        return leaveService.getAllLeaves(pageable);
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<LeaveDto> approveLeave(@PathVariable String id) {
        return leaveService.getLeaveById(id)
                .map(leave -> new ResponseEntity<>(leaveService.approveLeave(id), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
