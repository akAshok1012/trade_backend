package com.tm.app.dto;

import com.tm.app.entity.Contractor;

import lombok.Data;

@Data
public class ContractEmployeeDto {
	private String name;
	private Long phoneNumber;
	private String address;
	private Long aadhaarNumber;
	private String notes;
	private Contractor contractor;
	private String updatedBy;
}