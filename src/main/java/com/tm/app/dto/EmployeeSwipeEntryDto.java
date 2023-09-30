package com.tm.app.dto;

import java.sql.Date;

import com.tm.app.entity.Employee;
import com.tm.app.enums.SwipeType;

import lombok.Data;

@Data
public class EmployeeSwipeEntryDto {

	private Employee employee;
	private Date swipeDate;
	private SwipeType swipeType;
	private String remarks;
	private String updatedBy;

}
