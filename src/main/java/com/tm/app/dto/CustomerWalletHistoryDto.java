package com.tm.app.dto;

import com.tm.app.entity.CustomerWallet;
import com.tm.app.enums.TransactionType;

import lombok.Data;

@Data
public class CustomerWalletHistoryDto {

	private CustomerWallet customerWallet;
	private TransactionType transactionType;
	private Float amount;
	private String updatedBy;

}
