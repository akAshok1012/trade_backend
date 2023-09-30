package com.tm.app.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class EmployeePayConfigurationDto {
	
	private Integer monthlyLeavesAllowed;
	private Float overtimeRate;
	private Float attendanceBonus;
	private Date attendanceClosingDate;
	private String updatedBy;

}
