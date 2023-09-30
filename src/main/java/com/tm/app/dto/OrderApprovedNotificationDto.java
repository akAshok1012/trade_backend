package com.tm.app.dto;

import com.tm.app.enums.OrderStatus;

import lombok.Data;

@Data
public class OrderApprovedNotificationDto {

	private Integer orderId;
	private String name;
	private OrderStatus orderStatus;
}
