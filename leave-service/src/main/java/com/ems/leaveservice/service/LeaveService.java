package com.ems.leaveservice.service;

import com.ems.leaveservice.dto.LeaveDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LeaveService {
    LeaveDto applyLeave(LeaveDto leaveDto);
    Page<LeaveDto> getAllLeaves(Pageable pageable);
    Optional<LeaveDto> getLeaveById(String id);
    LeaveDto approveLeave(String id);
}
