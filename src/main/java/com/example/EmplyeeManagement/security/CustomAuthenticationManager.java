package com.example.EmplyeeManagement.security;

import com.example.EmplyeeManagement.entity.Employee;
import com.example.EmplyeeManagement.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {
    private EmployeeRepository employeeRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        if(isValidUser(email,password)){
            return new UsernamePasswordAuthenticationToken(email,password);
        }else{
            throw new AuthenticationException("invalid credentials") {
            } ;

        }
    }


    private boolean isValidUser(String email,String password){
        Employee employee = employeeRepository.findByEmail(email).get();
        return employee.getEmail().equals(email) && passwordEncoder.matches(password,employee.getPassword());
    }
}
