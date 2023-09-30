package com.tm.app.service;

import java.util.List;

import com.tm.app.entity.PaymentHistory;

public interface PaymentHistoryService {

	public List<PaymentHistory> getPaymentHistoryByCustomer(Long id);

}
