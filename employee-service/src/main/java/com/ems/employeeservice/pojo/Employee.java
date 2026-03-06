package com.ems.employeeservice.pojo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "employee")
public class Employee {
    @Id
    private String id;

    @NotBlank(message = "Employee name cannot be blank")
    private String employeeName;

    @NotBlank(message = "Employee email cannot be blank")
    @Email(message = "Invalid email format")
    private String employeeEmail;

    @NotNull(message = "Employee phone cannot be null")
    private Long employeePhone;

    private String employeeGender;

    @NotBlank(message = "Employee role cannot be blank")
    private String employeeRole;
}
