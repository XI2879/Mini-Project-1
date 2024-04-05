package com.example.EmplyeeManagement.controller;

import com.example.EmplyeeManagement.dto.EmployeeDto;
import com.example.EmplyeeManagement.dto.EmployeeGetDto;
import com.example.EmplyeeManagement.dto.EmployeeLoginRequestDto;
import com.example.EmplyeeManagement.exception.AccessDeniedException;
import com.example.EmplyeeManagement.security.JwtUtil;
import com.example.EmplyeeManagement.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/employee/")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody EmployeeLoginRequestDto requestDto){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                requestDto.getEmail(),
                requestDto.getPassword()
        );
        authenticationManager.authenticate(token);
        String jwt = jwtUtil.generate(requestDto.getEmail());
        return ResponseEntity.ok(jwt);

    }



    // Get all employees
    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER\")")
    @GetMapping("all")
    public ResponseEntity<List<EmployeeGetDto>> getAllEmployees() {

        List<EmployeeGetDto> employees = employeeService.getAllEmployee();
        return ResponseEntity.ok(employees);
    }



    // Get employee by ID
    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER\")")
    @GetMapping("{id}")
    public ResponseEntity<EmployeeGetDto> getEmployeeById(@PathVariable("id") Long id) {
        EmployeeGetDto employee = employeeService.getEmployee(id);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create a new employee

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            // Throw AccessDeniedException if the user does not have the required role
            throw new AccessDeniedException("Access is denied");
        }

        EmployeeDto createdEmployee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    // Update an existing employee
    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER\")")
    @PutMapping("{id}")
    public ResponseEntity<EmployeeDto> updateEmployee( @RequestBody @Valid EmployeeDto employeeDto,@PathVariable("id") Long id) {
        EmployeeDto updatedEmployee = employeeService.updateEmployee(employeeDto,id);
        if (updatedEmployee != null) {
            return ResponseEntity.ok(updatedEmployee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete an employee

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            // Throw AccessDeniedException if the user does not have the required role
            throw new AccessDeniedException("Access is denied");
        }

        employeeService.deleteEmployee(id);
        return new ResponseEntity<>("Deleted employee successfully",HttpStatus.OK);
    }

}
