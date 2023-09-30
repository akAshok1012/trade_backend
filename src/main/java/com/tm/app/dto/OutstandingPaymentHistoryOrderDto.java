package com.tm.app.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class OutstandingPaymentHistoryOrderDto {

	private Integer orderId;
	private Float paymentAmount;
	private Float paidAmount;
	private Float balanceAmount;
	private String name;
	private Timestamp dateTime;
	
}
