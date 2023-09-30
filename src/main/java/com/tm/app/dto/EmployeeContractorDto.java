package com.tm.app.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeContractorDto {

	private Long id;
	private String firstName;
	private String lastName;
	private Date hireDate;
	private Date endDate;
	private Float dailyRate;
	private String title;
	private String department;
	private String updatedBy;

}
