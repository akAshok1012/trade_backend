package com.tm.app.dto;

import java.util.List;

import com.tm.app.entity.OrderItem;
import com.tm.app.entity.ShipmentDetails;

import lombok.Data;

@Data
public class ShipmentParcelDto {

	private List<OrderItem> orderItem;
	private ShipmentDetails shipment;
}
