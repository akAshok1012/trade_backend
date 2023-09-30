package com.tm.app.dto;

import com.tm.app.enums.OrderStatus;

import lombok.Data;

@Data
public class OrderPlacedNotificationDto {

	private Integer orderId;
	private String name;
	private Float totalAmount;
	private OrderStatus orderStatus;
}
