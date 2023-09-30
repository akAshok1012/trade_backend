package com.tm.app.dto;

import com.tm.app.entity.OrderItem;

import lombok.Data;

@Data
public class ItemAndQuantityDto {
	private OrderItem orderItem;
	private Integer shippingQuantity;
}
