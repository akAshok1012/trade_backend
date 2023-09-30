package com.tm.app.dto;

import java.sql.Date;

import com.tm.app.entity.EmployeeContractor;
import com.tm.app.entity.EmployeeDepartment;
import com.tm.app.entity.EmployeeType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

	private Long id;
	private String name;
	private String designation;
	private Date dateOfBirth;
	private EmployeeDepartment employeeDepartment;
	private Date dateOfJoining;
	private String pfNumber;
	private String esiNumber;
	private String panNumber;
	private Long uanNumber;
	private Long aadhaarNumber;
	private Long phoneNumber;
	private String email;
	private String address;
	private EmployeeType employeeType;
	private EmployeeContractor employeeContractor;
	private String updatedBy;
	private Integer leaveBalance;
}