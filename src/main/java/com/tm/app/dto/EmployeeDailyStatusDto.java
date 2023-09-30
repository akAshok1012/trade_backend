package com.tm.app.dto;

import java.sql.Date;

import com.tm.app.entity.Employee;
import com.tm.app.enums.DailyStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDailyStatusDto {
	private Employee employee;
	private String notes;
	private Date date;
	private String updatedBy;
	private DailyStatus dailyStatus;

}
