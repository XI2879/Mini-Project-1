package com.example.EmplyeeManagement.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private Long id;
    @NotEmpty(message = "first name cannot be empty")
    private String firstName;
    @NotEmpty(message = "last name cannot be empty")
    private String lastName;
    @Email(message = "email address is not valid")
    private String email;
    private String password;
}
