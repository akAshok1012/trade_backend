package com.tm.app.dto;

import com.tm.app.entity.Employee;

import lombok.Data;

@Data
public class EmployeeAttendanceDto {

	private Employee employee;
	private Float totalHours;
	private Float overtimeHours;
	private String updatedBy;

}
