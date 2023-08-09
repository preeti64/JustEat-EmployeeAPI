package com.example.employeeapi.employee;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    
    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    void testGetAllEmployees() throws Exception {
        Employee employee1 = new Employee(1L, "alex@google.com", "Alex Botham", "1990-03-21", null);
        Employee employee2 = new Employee(2L, "jane@google.com", "Jane Tait", "1997-02-11", null);
        List<Employee> mockEmployees = Arrays.asList(employee1, employee2); 
        
        when(employeeRepository.findAll()).thenReturn(mockEmployees);

        mockMvc.perform(get("/employees"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].fullName").value("Alex Botham"))
            .andExpect(jsonPath("$[0].birthday").value("1990-03-21"))
            .andExpect(jsonPath("$[1].fullName").value("Jane Tait"))
            .andExpect(jsonPath("$[1].birthday").value("1997-02-11"));

    }

    @Test
    void testCreateEmployee() throws Exception {
    	Employee mockEmployee = new Employee(1L, "alex@google.com", "Alex Botham", "1990-03-21", null);
        when(employeeRepository.findByEmail(anyString())).thenReturn(null);
        when(employeeRepository.save(Mockito.any())).thenReturn(mockEmployee);
        
        mockMvc.perform(post("/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"fullName\": \"Alex Botham\", \"email\": \"alex@google.com\"}")
        )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.fullName").value("Alex Botham"))
            .andExpect(jsonPath("$.email").value("alex@google.com"));
    }

    @Test
    void testGetEmployeeByUuid() throws Exception {
        Employee mockEmployee = new Employee(1L, "alex@google.com", "Alex Botham", "1990-03-21", null);
        when(employeeRepository.findByUuid(1L)).thenReturn(mockEmployee);

        mockMvc.perform(get("/employees/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.fullName").value("Alex Botham"))
            .andExpect(jsonPath("$.email").value("alex@google.com"));
    }

    @Test
    void testUpdateEmployee() throws Exception {
        Employee existingEmployee = new Employee(1L, "alex@google.com", "Alex Botham", "1990-03-21", null);
        when(employeeRepository.findByUuid(1L)).thenReturn(existingEmployee);
        when(employeeRepository.save(any())).thenReturn(existingEmployee);

        mockMvc.perform(put("/employees/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"fullName\": \"Alex Botham (Updated)\", \"email\": \"alex@google.com\"}")
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.fullName").value("Alex Botham (Updated)"))
            .andExpect(jsonPath("$.email").value("alex@google.com"));
    }

    @Test
    void testDeleteEmployee() throws Exception {
        Employee existingEmployee = new Employee(1L, "alex@google.com", "Alex Botham", "1990-03-21", null);
        when(employeeRepository.findByUuid(1L)).thenReturn(existingEmployee);

        mockMvc.perform(delete("/employees/1"))
            .andExpect(status().isNoContent());

        verify(employeeRepository, times(1)).delete(existingEmployee);
    }
}