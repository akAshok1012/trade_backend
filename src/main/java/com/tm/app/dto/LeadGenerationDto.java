package com.tm.app.dto;

import java.sql.Date;
import java.util.List;

import com.tm.app.entity.ItemMaster;
import com.tm.app.enums.LeadStatus;

import lombok.Data;

@Data
public class LeadGenerationDto {

	private String name;
	private String email;
	private String phone;
	private String location;
	private List<ItemMaster> intrestedIn;
	private String referralSourceType;
	private String referralName;
	private String referralPhone;
	private String notes;
	private LeadStatus status;
	private Date followupDate;
	private Integer noOfFollowups;
	private String referenceImageString;
	private Boolean isSampleProvided;
	private String updatedBy;

}
