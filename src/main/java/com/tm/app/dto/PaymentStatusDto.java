package com.tm.app.dto;

import java.sql.Timestamp;

import com.tm.app.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentStatusDto {

	private Integer salesId;
	private String customerName;
	private PaymentStatus paymentStatus;
	private Float paidAmount;
	private Float paymentAmount;
	private Timestamp paymentDate;
}
