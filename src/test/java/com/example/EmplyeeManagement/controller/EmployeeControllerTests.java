package com.example.EmplyeeManagement.controller;

import com.example.EmplyeeManagement.dto.EmployeeDto;
import com.example.EmplyeeManagement.service.Impl.EmployeeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class EmployeeControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeServiceImpl employeeService;
    @Autowired
    private ObjectMapper objectMapper;
    private EmployeeDto employeeDto;

    @BeforeEach
    public void setUp() {
        employeeDto = new EmployeeDto();
        employeeDto.setFirstName("john");
        employeeDto.setLastName("doe");
        employeeDto.setEmail("john@gmail.com");
        employeeDto.setPassword("john");
        employeeDto.setId(1L);
    }

    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        // Mocking the behavior of SecurityContextHolder to return admin roles
//        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
//        Authentication authentication = UsernamePasswordAuthenticationToken.builder()
//                .principal("user")
//                .authorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")))
//                .build();
//        securityContext.setAuthentication(authentication);
//        SecurityContextHolder.setContext(securityContext);

        given(employeeService.createEmployee(any(EmployeeDto.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/employee/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDto)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(employeeDto.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employeeDto.getLastName())))
                .andExpect(jsonPath("$.email", is(employeeDto.getEmail())))
                .andExpect(jsonPath("$.password", is(employeeDto.getPassword())));
    }
}
