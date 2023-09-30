package com.tm.app.dto;

import java.sql.Date;

import com.tm.app.entity.Employee;
import com.tm.app.enums.EmployeeLeaveStatus;

import lombok.Data;

@Data
public class EmployeeLeaveStatusDto {

	private Long id;
	private Employee employee;
	private EmployeeLeaveStatus employeeLeaveStatus;
	private Date startDate;
	private Date endDate;
}
