package com.tm.app.dto;

import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
public class MachineryDto {

	private Long id;
	private String name;
	private String description;
	private String serialNumber;
	private String manufacturer;
	private String modelNumber;
	private Date dateOfPurchase;
	private Float purchaseCost;
	@CreationTimestamp
	private Timestamp updatedAt;
	private String updatedBy;
}
