package com.tm.app.dto;

import java.sql.Date;

import com.tm.app.entity.VehicleType;

import lombok.Data;

@Data
public class VehicleDetailsDto {
	private VehicleType vehicleType;
	private String vehicleRegistrationNumber;
	private String vehicleMake;
	private String vehicleModel;
	private String chassisNumber;
	private String rcNumber;
	private String insuranceCompanyName;
	private String policyNo;
	private Date insuranceStartDate;
	private Date insuranceEndDate;
	private String fastTag;
	private Date purchaseDate;
	private Float purchasePrice;
	private String updatedBy;
}
