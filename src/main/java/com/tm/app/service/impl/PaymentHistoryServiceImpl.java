package com.tm.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tm.app.entity.Customer;
import com.tm.app.entity.PaymentHistory;
import com.tm.app.repo.CustomerRepo;
import com.tm.app.repo.PaymentHistoryRepository;
import com.tm.app.service.PaymentHistoryService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class PaymentHistoryServiceImpl implements PaymentHistoryService {

	@Autowired
	private PaymentHistoryRepository paymentHistoryRepository;
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Override
	public List<PaymentHistory> getPaymentHistoryByCustomer(Long id) {
		log.info("service starts");
		Customer customer= customerRepo.findById(id).orElseThrow();
		return paymentHistoryRepository.getPaymentHistoryByCustomer(customer);
	}
}