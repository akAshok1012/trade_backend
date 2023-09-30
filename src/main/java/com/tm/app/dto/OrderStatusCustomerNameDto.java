package com.tm.app.dto;

import java.sql.Timestamp;

import com.tm.app.enums.OrderStatus;

import lombok.Data;

@Data
public class OrderStatusCustomerNameDto {

	private Long user;
	private Integer orderId;
	private OrderStatus orderStatus;
	private String name;
	private String rejectReason;
	private Timestamp updatedAt;
	
}
