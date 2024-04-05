package com.example.EmplyeeManagement.service;

import com.example.EmplyeeManagement.dto.EmployeeDto;
import com.example.EmplyeeManagement.entity.Employee;
import com.example.EmplyeeManagement.repository.EmployeeRepository;
import com.example.EmplyeeManagement.service.Impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;
    private Employee employee;
    @Mock
    private ModelMapper modelMapper;
    private EmployeeDto employeeDto;


    @BeforeEach
    public void setUp() {
        employeeDto = new EmployeeDto();
        employeeDto.setFirstName("john");
        employeeDto.setLastName("doe");
        employeeDto.setEmail("john@gmail.com");
        employeeDto.setPassword("john");

    }

    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnSavedEmployeeObject() {
        // Mock the behavior of ModelMapper

        given(modelMapper.map(any(EmployeeDto.class), eq(Employee.class))).willReturn(new Employee());

        // Mock the behavior of EmployeeRepository
        given(employeeRepository.findByEmail(employeeDto.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(any(Employee.class))).willReturn(new Employee());

        // Call the service method
        EmployeeDto savedEmployee = employeeService.createEmployee(employeeDto);
        verify(employeeRepository).findByEmail("john@gmail.com");

        // Assertions
       assertThat(savedEmployee).isNotNull();
    }
}
