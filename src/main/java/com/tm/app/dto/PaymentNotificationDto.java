package com.tm.app.dto;

import com.tm.app.entity.CreditPaymentTrack;

import lombok.Data;

@Data
public class PaymentNotificationDto {

	private Integer salesId;
	private String name;
	private String days;
	private CreditPaymentTrack creditPaymentTrack;

}
