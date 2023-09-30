package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.PaymentTypeDto;
import com.tm.app.entity.PaymentType;

public interface PaymentTypeService {

	public List<PaymentType> getPaymentTypes();

	public PaymentType savePaymentType(PaymentTypeDto paymentTypeDto);

	public PaymentType updatePaymentType(Long id, PaymentTypeDto paymentTypeDto);

	public void deletePaymentType(Long id);

	public PaymentType getPaymentTypeById(Long id);

	public Page<PaymentType> getPaymentTypeList(DataFilter dataFilter);

}
