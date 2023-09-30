package com.tm.app.dto;

import com.tm.app.enums.ShipmentStatus;

import lombok.Data;

@Data
public class ShipmentCustomerUserStatusDto {

	private Integer salesId;
	private String name;
	private ShipmentStatus shipmentStatus;
	private Long user;
}
