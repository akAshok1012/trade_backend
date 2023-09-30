package com.tm.app.dto;

import java.sql.Date;

import com.tm.app.entity.Shipment;
import com.tm.app.enums.ShipmentStatus;

import lombok.Data;

@Data
public class ShipmentDetailsDto {

	private String carrier;
	private Date shipmentDate;
	private Integer trackingNumber;
	private Integer salesId;
	private Long item;
	private Long orderItemId;
	private Integer orderQuantity;
	private Integer shippedQuantity;
	private Integer balanceQuantity;
	private ShipmentStatus status;
	private Shipment shipmentId;

}
