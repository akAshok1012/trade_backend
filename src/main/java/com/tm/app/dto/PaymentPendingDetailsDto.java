package com.tm.app.dto;

import com.tm.app.entity.Customer;
import com.tm.app.enums.PaymentStatus;

import lombok.Data;
@Data
public class PaymentPendingDetailsDto {

	private Integer orderId;
	
	private Float totalAmount;
	
	private Float paidAmount;

	private Float balanceAmount;
	
	private Customer customer;
	
	private PaymentStatus paymentStatus;
}
