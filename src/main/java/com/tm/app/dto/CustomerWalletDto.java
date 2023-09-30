package com.tm.app.dto;

import com.tm.app.entity.Customer;

import lombok.Data;

@Data
public class CustomerWalletDto {

	private Customer customer;
	private Float addAmount;
	private String updatedBy;
	private String note;

}
