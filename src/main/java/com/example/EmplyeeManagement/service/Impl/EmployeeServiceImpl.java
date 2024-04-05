package com.example.EmplyeeManagement.service.Impl;

import com.example.EmplyeeManagement.dto.EmployeeDto;
import com.example.EmplyeeManagement.dto.EmployeeGetDto;
import com.example.EmplyeeManagement.entity.Employee;
import com.example.EmplyeeManagement.exception.EmailAlreadyExistsException;
import com.example.EmplyeeManagement.exception.ResourceNotFoundException;
import com.example.EmplyeeManagement.repository.EmployeeRepository;
import com.example.EmplyeeManagement.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;


    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee =modelMapper.map(employeeDto,Employee.class);
        Optional<Employee> employeeOptional=employeeRepository.findByEmail(employee.getEmail());
        if(employeeOptional.isPresent()){
            throw new EmailAlreadyExistsException("Email alreday exists exception");
        }
        return modelMapper.map(employeeRepository.save(employee),EmployeeDto.class);
    }

    @Override
    public List<EmployeeGetDto> getAllEmployee() {
        List<Employee> employees=employeeRepository.findAll();
        return employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeGetDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeGetDto getEmployee(Long id) {
        Employee employee =employeeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("employee","id", id));
        return modelMapper.map(employee,EmployeeGetDto.class);
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto,Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("employee","id", id));
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setPassword(employeeDto.getPassword());
        return modelMapper.map(employeeRepository.save(employee),EmployeeDto.class);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("employee","id", id));
        employeeRepository.deleteById(id);
    }
}
