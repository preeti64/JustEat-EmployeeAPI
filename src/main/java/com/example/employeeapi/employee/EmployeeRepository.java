package com.example.employeeapi.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	Employee findByUuid(long uuid);

    Employee findByEmail(String email);

}
