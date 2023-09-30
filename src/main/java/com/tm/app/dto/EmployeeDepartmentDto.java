package com.tm.app.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDepartmentDto {
	
	private Long id;
	private String departmentName;
	private String updatedBy;
	private Timestamp updatedAt;

}
