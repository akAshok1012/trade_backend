package com.tm.app.dto;

import java.sql.Date;
import java.util.List;

import com.tm.app.entity.ContractEmployee;
import com.tm.app.entity.Contractor;

import lombok.Data;
@Data
public class ContractDetailsDto {
	private String contractName;
	private Contractor contractor;
	private Date startDate;
	private Date endDate;
	private Float contractAmount;
	private List<ContractEmployee> contractEmployees;
	private String notes;
	private String updatedBy;
}