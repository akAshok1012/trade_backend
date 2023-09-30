package com.tm.app.dto;

import com.tm.app.entity.OrderItem;
import com.tm.app.enums.ShipmentStatus;

import lombok.Data;

@Data
public class ShipmentItemDto {
	private OrderItem orderItem; 
	private Integer orderedQuantity;
	private Integer shippedQuantity;
	private Integer balanceQuantity;
	private ShipmentStatus shipmentStatus;
}
