package com.example.employeeapi.employee;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    void testGetAllEmployees() throws Exception {
        List<Employee> mockEmployees = Arrays.asList(
            new Employee(1L, "alex@google.com", "Alex Botham", "1990-03-21", null),
            new Employee(2L, "jane@google.com", "Jane Tait", "1997-02-11", null)
        );

        when(employeeRepository.findAll()).thenReturn(mockEmployees);

        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].fullName", Matchers.is("Alex Botham")))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].fullName", Matchers.is("Jane Tait")));

    }

    @Test
    void testCreateEmployee() throws Exception {
        Employee mockEmployee = new Employee(1L, "alex@google.com", "Alex Botham", "1990-03-21", null);
        when(employeeRepository.save(Mockito.any())).thenReturn(mockEmployee);

        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"fullName\": \"Alex Botham\", \"email\": \"alex@google.com\"}")
            )
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("Alex Botham"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("alex@google.com"));
    }
    
    @Test
    void testGetEmployeeByUuid() throws Exception {
        Employee mockEmployee = new Employee(1L, "alex@google.com", "Alex Botham", "1990-03-21", null);
        when(employeeRepository.findByUuid(Mockito.anyLong())).thenReturn(mockEmployee);

        mockMvc.perform(MockMvcRequestBuilders.get("/employees/1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("Alex Botham"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("alex@google.com"));
    }

    @Test
    void testUpdateEmployee() throws Exception {
        Employee existingEmployee = new Employee(1L, "alex@google.com", "Alex Botham", "1990-03-21", null);
        Employee updatedEmployee = new Employee(1L, "hanns@yahoo.com", "Hanns Williams", "1988-05-12", null);
        when(employeeRepository.findByUuid(Mockito.anyLong())).thenReturn(existingEmployee);
        when(employeeRepository.findByEmail(Mockito.anyString())).thenReturn(null);
        when(employeeRepository.save(Mockito.any())).thenReturn(updatedEmployee);

        mockMvc.perform(MockMvcRequestBuilders.put("/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"fullName\": \"Hanns Williams\", \"email\": \"hanns@yahoo.com\"}")
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("Hanns Williams"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("hanns@yahoo.com"));
    }

    @Test
    void testDeleteEmployee() throws Exception {
        Employee existingEmployee = new Employee(1L, "alex@google.com", "Alex Botham", "1990-03-21", null);
        when(employeeRepository.findByUuid(Mockito.anyLong())).thenReturn(existingEmployee);

        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/1"))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}

