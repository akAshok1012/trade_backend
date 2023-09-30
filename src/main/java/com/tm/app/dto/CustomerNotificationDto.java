package com.tm.app.dto;

import java.sql.Timestamp;

import com.tm.app.enums.OrderStatus;

import lombok.Data;

@Data
public class CustomerNotificationDto {

	private Integer orderId;
	private String name;
	private OrderStatus orderStatus;
	private Timestamp dateTime;
	private Long user;
}
