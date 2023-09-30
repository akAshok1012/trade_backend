package com.tm.app.dto;

import com.tm.app.enums.PaymentStatus;
import com.tm.app.enums.SalesStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomersPaymentStatusOrderStatusDto {

	public PaymentStatus paymentStatus;

	public String name;

	public SalesStatus salesStatus;

}
