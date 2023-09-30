package com.tm.app.dto;

import com.tm.app.entity.Customer;
import com.tm.app.entity.RejectReason;
import com.tm.app.enums.OrderStatus;

import lombok.Data;

@Data
public class OrderDto {
	
	private Integer orderId;
	private OrderStatus orderStatus;
	private Customer customer;
	private RejectReason rejectReason;
	private String updatedBy;

}
