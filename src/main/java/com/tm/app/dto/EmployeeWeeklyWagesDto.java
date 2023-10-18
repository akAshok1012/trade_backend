package com.tm.app.dto;

import java.sql.Date;

import com.tm.app.entity.Employee;

import lombok.Data;

@Data
public class EmployeeWeeklyWagesDto {

	private Employee employee;
	private Date workStartDate;
	private Date workEndDate;
	private Integer weeklyWorkedHours;
	private Integer hourlyPay;
	private Integer weeklyTotalPay;
	private String updatedBy;

}
