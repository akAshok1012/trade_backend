package com.tm.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeTypeDto {

	private Long id;
	private String employeeType;
	private String description;
	private String updatedBy;

}
