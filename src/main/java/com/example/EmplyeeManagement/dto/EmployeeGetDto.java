package com.example.EmplyeeManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeGetDto {
    private String firstName;
    private String lastName;
    private String email;
}
