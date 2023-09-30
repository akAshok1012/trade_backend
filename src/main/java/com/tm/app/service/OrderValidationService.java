package com.tm.app.service;

import java.util.List;

import com.tm.app.dto.OrderValidationDto;
import com.tm.app.entity.OrderValidation;

public interface OrderValidationService {

	public OrderValidation saveOrderValidation(OrderValidationDto orderValidationDto);

	public List<OrderValidation> getOrderValidations();

	public OrderValidation getOrderValidationById(Long id);

	public void deleteOrderValidationById(Long id);

	public OrderValidation updateOrderValidation(Long id, OrderValidationDto orderValidationDto);

}
