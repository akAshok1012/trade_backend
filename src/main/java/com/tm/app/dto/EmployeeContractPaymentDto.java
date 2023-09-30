package com.tm.app.dto;

import java.sql.Date;

import com.tm.app.entity.ContractDetails;
import com.tm.app.enums.PaymentBasis;

import lombok.Data;

@Data
public class EmployeeContractPaymentDto {
	private ContractDetails contractDetails;
	private Date paymentDate;
	private Float amountPaid;
	private Float totalAmount;
	private Float amountBalance;
	private PaymentBasis paymentBasis;
	private String notes;
	private String updatedBy;
}