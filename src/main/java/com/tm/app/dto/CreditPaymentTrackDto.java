package com.tm.app.dto;

import java.sql.Date;
import java.sql.Timestamp;

import com.tm.app.entity.Customer;
import com.tm.app.entity.Payment;

import lombok.Data;

@Data
public class CreditPaymentTrackDto {
	
	private Long id;
	private Payment payment;
	private Customer customer;
	private Integer salesId;
	private Float pendingAmount;
	private Float paidAmount;
	private Timestamp orderDateTime;
	private Timestamp notificationSendAt;
	private String description;
	private Date dueDate;
}
