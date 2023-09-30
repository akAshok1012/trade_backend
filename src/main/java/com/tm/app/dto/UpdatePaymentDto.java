package com.tm.app.dto;

import com.tm.app.entity.PaymentType;
import com.tm.app.enums.PaymentStatus;

import lombok.Data;

@Data
public class UpdatePaymentDto {
	private Integer salesId;
	private Float paidAmount;
	private PaymentStatus paymentStatus;
	private String updatedBy;
	private PaymentType paymentType;
}
