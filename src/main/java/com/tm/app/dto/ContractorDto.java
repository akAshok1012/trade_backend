package com.tm.app.dto;

import com.tm.app.enums.ContractorType;

import lombok.Data;

@Data
public class ContractorDto {
	private String name;
	private Long phoneNumber;
	private String email;
	private String address;
	private Long aadhaarNumber;
	private String panNumber;
	private ContractorType contractorType;
	private String notes;
	private String updatedBy;
}
