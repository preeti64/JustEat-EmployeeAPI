package com.example.employeeapi.employee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
public class Employee {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long uuid;
	    
	    @NotBlank(message = "Email is required")
	    @Email(message = "Email format is invalid")
	    @Column(unique = true)
	    private String email;
	    
	    private String fullName;
	    private String birthday;
	    private List<String> hobbies;
	    
	    public Employee() {
			super();
		}
		
		public Employee(Long uuid, String email, String fullName, String birthday, List<String> hobbies) {
			super();
			this.uuid = uuid;
			this.email = email;
			this.fullName = fullName;
			this.birthday = birthday;
			this.hobbies = hobbies;
		}

		public Long getUuid() {
			return uuid;
		}
		public void setUuid(Long uuid) {
			this.uuid = uuid;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getFullName() {
			return fullName;
		}
		public void setFullName(String fullName) {
			this.fullName = fullName;
		}
		public String getBirthday() {
			return birthday;
		}
		public void setBirthday(String birthday) {
			this.birthday = birthday;
		}
		public List<String> getHobbies() {
			return hobbies;
		}
		public void setHobbies(List<String> hobbies) {
			this.hobbies = hobbies;
		}
		
	}



