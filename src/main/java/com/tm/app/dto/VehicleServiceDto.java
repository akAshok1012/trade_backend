package com.tm.app.dto;

import java.sql.Date;
import java.util.List;

import com.tm.app.entity.VehicleDetails;
import com.tm.app.entity.VehicleServiceDetails;

import lombok.Data;

@Data
public class VehicleServiceDto {
	private List<VehicleServiceDetails> vehicleServiceDetails;
	private VehicleDetails vehicleDetails;
	private String serviceProvider;
	private String notes;
	private Date currentService;
	private Date nextService;
	private String updatedBy;
}
