package com.ems.leaveservice.controller;

import com.ems.leaveservice.dto.LeaveDto;
import com.ems.leaveservice.service.LeaveService;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class LeaveControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LeaveService leaveService;

    @InjectMocks
    private LeaveController leaveController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(leaveController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void testApplyLeave() throws Exception {
        LeaveDto leaveDto = new LeaveDto();
        leaveDto.setId(UUID.randomUUID().toString());
        leaveDto.setEmployeeId("1");
        when(leaveService.applyLeave(any(LeaveDto.class))).thenReturn(leaveDto);

        mockMvc.perform(post("/api/leaves/apply")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"employeeId\":\"1\",\"leaveType\":\"SICK\",\"startDate\":\"2024-01-01\",\"endDate\":\"2024-01-02\",\"reason\":\"Fever\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.employeeId").value("1"));
    }

    @Test
    public void testGetAllLeaves() throws Exception {
        LeaveDto leave1 = new LeaveDto();
        leave1.setEmployeeId("1");
        LeaveDto leave2 = new LeaveDto();
        leave2.setEmployeeId("2");
        List<LeaveDto> leaves = Arrays.asList(leave1, leave2);
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<LeaveDto> leavePage = new PageImpl<>(leaves, pageRequest, leaves.size());
        when(leaveService.getAllLeaves(any(PageRequest.class))).thenReturn(leavePage);

        mockMvc.perform(get("/api/leaves/viewall?page=0&size=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].employeeId").value("1"))
                .andExpect(jsonPath("$.content[1].employeeId").value("2"));
    }

    @Test
    public void testApproveLeave() throws Exception {
        LeaveDto leaveDto = new LeaveDto();
        leaveDto.setStatus("APPROVED");
        when(leaveService.getLeaveById("1")).thenReturn(Optional.of(new LeaveDto()));
        when(leaveService.approveLeave("1")).thenReturn(leaveDto);

        mockMvc.perform(put("/api/leaves/approve/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("APPROVED"));
    }

    @Test
    public void testApproveLeaveNotFound() throws Exception {
        when(leaveService.getLeaveById("1")).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/leaves/approve/1"))
                .andExpect(status().isNotFound());
    }
}
