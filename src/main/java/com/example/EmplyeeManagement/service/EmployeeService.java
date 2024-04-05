package com.example.EmplyeeManagement.service;

import com.example.EmplyeeManagement.dto.EmployeeDto;
import com.example.EmplyeeManagement.dto.EmployeeGetDto;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<EmployeeGetDto> getAllEmployee();
    EmployeeGetDto getEmployee(Long id);
    EmployeeDto createEmployee(EmployeeDto employeeDto);
    EmployeeDto updateEmployee(EmployeeDto employeeDto,Long id);
    void deleteEmployee(Long id);


}
