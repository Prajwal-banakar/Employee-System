package com.ems.leave.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "leaves")
public class Leave {

    @Id
    private String id;
    private String employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private LeaveStatus status = LeaveStatus.PENDING;

}
