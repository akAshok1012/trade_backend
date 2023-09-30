package com.tm.app.dto;

import java.sql.Date;

import com.tm.app.entity.Employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDto {

	private Long id;
	private Employee employee;
	private Date date;
	private String notes;
}
