package com.tm.app.dto;

import com.tm.app.enums.OrderStatus;

import lombok.Data;

@Data
public class ManageSalesOrderDto {

	private Integer orderId;
	private Long customerId;
	private String customer;
	private OrderStatus orderStatus;

}
