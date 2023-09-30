package com.tm.app.dto;

import com.tm.app.enums.ShipmentStatus;

import lombok.Data;

@Data
public class ShipmentStatusDto {

	private Integer salesId;
	private String customerName;
	private ShipmentStatus shipmentStatus;
}
