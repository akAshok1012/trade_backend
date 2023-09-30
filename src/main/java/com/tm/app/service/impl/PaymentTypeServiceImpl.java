package com.tm.app.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.PaymentTypeDto;
import com.tm.app.entity.PaymentType;
import com.tm.app.repo.PaymentTypeRepo;
import com.tm.app.service.PaymentTypeService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentTypeServiceImpl implements PaymentTypeService {

	@Autowired
	private PaymentTypeRepo paymentTypeRepo;

	@Override
	@Transactional
	public PaymentType savePaymentType(PaymentTypeDto paymentTypeDto) {
		PaymentType paymentTypes = new PaymentType();
		try {
			if(paymentTypeRepo.existsByPaymentTypeEqualsIgnoreCase(paymentTypeDto.getPaymentType())) {
				throw new RuntimeException("Payment Type already exists!");
			}
			BeanUtils.copyProperties(paymentTypeDto, paymentTypes);
			paymentTypes = paymentTypeRepo.save(paymentTypes);
		} catch (Exception e) {
			log.error("[PAYMENT] adding payment type failed", e);
			throw new RuntimeException(e.getMessage());
		}
		return paymentTypes;
	}

	@Override
	public List<PaymentType> getPaymentTypes() {

		return paymentTypeRepo.findAll();
	}

	@Override
	public PaymentType getPaymentTypeById(Long id) {
		return paymentTypeRepo.getPaymentTypeById(id);
	}

	@Override
	@Transactional
	public PaymentType updatePaymentType(Long id, PaymentTypeDto paymentTypeDto) {
		PaymentType paymentTypes = paymentTypeRepo.findById(id).orElseThrow();
		try {
			if (Objects.nonNull(paymentTypeDto.getPaymentType())
					&& paymentTypeRepo.existsByPaymentTypeEqualsIgnoreCase(paymentTypeDto.getPaymentType())
					&& !paymentTypes.getPaymentType().equals(paymentTypeDto.getPaymentType())) {
				throw new RuntimeException("PaymentType Already exists");
			}
			BeanUtils.copyProperties(paymentTypeDto, paymentTypes);
			paymentTypes = paymentTypeRepo.save(paymentTypes);
		} catch (Exception e) {
			log.error("[PAYMENT] updating payment type failed", e);
			throw new RuntimeException(e.getMessage());
		}
		return paymentTypes;
	}

	@Override
	@Transactional
	public void deletePaymentType(Long id) {
		try {
			paymentTypeRepo.deleteById(id);
		} catch (Exception e) {
			log.error("[PAYMENT] deleting payment type failed", e);
			throw new RuntimeException("Deleting payment type failed");
		}
	}

	@Override
	public Page<PaymentType> getPaymentTypeList(DataFilter dataFilter) {
		return paymentTypeRepo.findByPaymentTypeLikeIgnoreCase(dataFilter.getSearch(),
				PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
						Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}
}