package com.tm.app.dto;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class CarrierDto {

	private Long id;
	private String carrierCompany; 
	private String contactName;
	private String contactEmail;
	private String contactPhone;
	private String address;
	private Timestamp updatedAt;
	private String updatedBy;

}
