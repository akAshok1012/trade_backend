package com.tm.app.dto;

import java.sql.Date;

import com.tm.app.entity.Machinery;

import lombok.Data;

@Data
public class MaintenanceDto {

	private Machinery machineryId;
	private Date maintenanceDate;
	private String description;
	private Float cost;
	private String technicianName;
	private String technicianPhoneNo;
	private Date nextMaintenanceDate;
	private String updatedBy;
}
