package com.example.employeeapi.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
@Validated
public class EmployeeController {
	
	@Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    
    @GetMapping("/{uuid}")
    public ResponseEntity<Employee> getEmployeeByUuid(@PathVariable String uuid) {
        Employee employee = employeeRepository.findByUuid(Long.parseLong(uuid));
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody Employee employee) {
       if (employeeRepository.findByEmail(employee.getEmail()) != null) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Email already exists");
       }
       Employee savedEmployee = employeeRepository.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
        
    }
    
    @PutMapping("/{uuid}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long uuid, @RequestBody Employee employee) {

    	Employee existingEmployee = employeeRepository.findByUuid(uuid);
        if (existingEmployee == null) {
            return ResponseEntity.notFound().build();
        }
        Employee employeeWithUpdatedEmail = employeeRepository.findByEmail(employee.getEmail());
        if (employeeWithUpdatedEmail != null && !employeeWithUpdatedEmail.getUuid().equals(uuid)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body("Employee with email already exists");
        }
        
        employee.setUuid(uuid);
        employeeRepository.save(employee);
        return ResponseEntity.ok(employee);
    }
    
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String uuid) {
        Employee existingEmployee = employeeRepository.findByUuid(Long.parseLong(uuid));
        if (existingEmployee != null) {
            employeeRepository.delete(existingEmployee);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
