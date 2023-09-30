package com.tm.app.dto;

import java.sql.Timestamp;

import com.tm.app.enums.PaymentStatus;

import lombok.Data;

@Data
public class PaymentCustomerDto {

	private Long user;
	private String name;
	private Integer salesId;
	private PaymentStatus paymentStatus;
	private Float paidAmount;
	private Float paymentAmount;
	private Timestamp paymentDate;
}
