package com.tm.app.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tm.app.dto.OrderValidationDto;
import com.tm.app.entity.OrderValidation;
import com.tm.app.repo.OrderValidationRepo;
import com.tm.app.service.OrderValidationService;

@Service
public class OrderValidationServiceImpl implements OrderValidationService {

	@Autowired
	private OrderValidationRepo orderValidationRepo;

	@Override
	public OrderValidation saveOrderValidation(OrderValidationDto orderValidationDto) {
		OrderValidation orderValidation = new OrderValidation();
		BeanUtils.copyProperties(orderValidationDto, orderValidation);
		return orderValidationRepo.save(orderValidation);
	}

	@Override
	public List<OrderValidation> getOrderValidations() {
		return orderValidationRepo.findAll();
	}

	@Override
	public OrderValidation getOrderValidationById(Long id) {
		return orderValidationRepo.findById(id).orElseThrow();
	}

	@Override
	public void deleteOrderValidationById(Long id) {
		orderValidationRepo.deleteById(id);
	}

	@Override
	public OrderValidation updateOrderValidation(Long id, OrderValidationDto orderValidationDto) {
		OrderValidation orderValidation = orderValidationRepo.findById(id).orElseThrow();
		orderValidation.setPlaceOrder(orderValidationDto.getPlaceOrder());
		orderValidation.setDescription(orderValidationDto.getDescription());
		orderValidation = orderValidationRepo.save(orderValidation);
		return orderValidation;
	}
}