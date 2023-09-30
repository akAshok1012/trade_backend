package com.tm.app.dto;

import java.sql.Date;

import com.tm.app.entity.Employee;

import lombok.Data;

@Data
public class EmployeeMonthlyPayDto {

	private Float basicPay;
	private Float hra;
	private Float da;
	private Float conveyanceAllowance;
	private Float medicalAllowance;
	private Float ita;
	private Float specialAllowance;
	private Float bonus;
	private Float epf;
	private Float esi;
	private Float deductions;
	private Float earnings;
	private Float netPay;
	private String updatedBy;
	private Employee employee;
	private Date payDateTime;
	private Integer monthlyLeavesAllowed;
	private Float overtimeRate;
	private Float attendanceBonus;
	private Integer totalDays;
	private Integer totalWorkingDays;
}
