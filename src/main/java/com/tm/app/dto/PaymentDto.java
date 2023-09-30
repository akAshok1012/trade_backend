package com.tm.app.dto;

import java.sql.Date;

import com.tm.app.entity.Customer;
import com.tm.app.entity.PaymentType;
import com.tm.app.enums.BillingStatus;
import com.tm.app.enums.PaymentStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class PaymentDto {

	private Integer salesId;
	private Customer customerId;
	private Date billingDate;
	private String billingNotes;
	private Date paymentDate;
	private Float totalOrderAmount;
	private Float totalPaidAmount;
	private Float balanceAmount;
	private String paymentNotes;
	private String updatedBy;
	@Enumerated(EnumType.STRING)
	private BillingStatus billingStatus;
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;
	
	private PaymentType paymentType;
}
