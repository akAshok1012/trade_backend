package com.tm.app.dto;

import java.sql.Date;

import com.tm.app.entity.LeadGeneration;
import com.tm.app.enums.LeadStatus;

import lombok.Data;

@Data
public class LeadFollowUpDto {

	private LeadGeneration leadGeneration;
	private Date followUpDate;
	private String notes;
	private LeadStatus status;
	private String createdBy;
	private String updatedBy;

}
