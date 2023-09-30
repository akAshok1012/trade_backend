package com.tm.app.dto;

import com.tm.app.entity.CreditPaymentTrack;
import com.tm.app.entity.CustomerWallet;
import com.tm.app.entity.Payment;

import lombok.Data;

@Data
public class ShipmentPaymentCreditPaymentWalletDto {
	private Payment payment;
	private CreditPaymentTrack creditPaymentTrack;
	private CustomerWallet customerWallet;
}
