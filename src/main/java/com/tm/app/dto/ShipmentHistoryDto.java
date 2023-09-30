package com.tm.app.dto;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class ShipmentHistoryDto {
	private Integer salesId;
	private Date shipmentDate;
	private String itemName;
	private Integer orderQuantity;
	private Integer shippedQuantity;
	private Integer balanceQuantity;
	private Timestamp updatedAt;
	public String trackingNumber;
	private String carrier;
	private String unitName;
	private String brandName;
}
