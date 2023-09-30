package com.tm.app.dto;

import java.sql.Date;

import com.tm.app.entity.Employee;

import lombok.Data;

@Data
public class EmployeePayHoursDto {

	private Employee employee;
	private Date workDate;
	private Integer hoursWorked;
	private Integer hourlyPay;
	private String updatedBy;

}
