package com.tm.app.dto;

import com.tm.app.enums.PaymentStatus;

import lombok.Data;

@Data
public class SalesViewDto {
	private Integer salesId;
	private String customerName;
	private PaymentStatus paymentStatus;
}