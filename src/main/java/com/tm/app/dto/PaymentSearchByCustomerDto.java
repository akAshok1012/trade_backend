package com.tm.app.dto;

import com.tm.app.enums.PaymentStatus;

import lombok.Data;

@Data
public class PaymentSearchByCustomerDto {

	private Long id;
	
	private String name;
	
	private Integer salesId;
	
	private PaymentStatus paymentStatus;
	
	private Integer paymentAmount;
	
	private Integer balanceAmount;
	
	private Integer paidAmount;
	
}
