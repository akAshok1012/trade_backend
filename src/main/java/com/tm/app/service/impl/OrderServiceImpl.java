package com.tm.app.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tm.app.dto.OrderDto;
import com.tm.app.entity.Order;
import com.tm.app.repo.OrderRepo;
import com.tm.app.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepo orderRepo;

	@Override
	public Order saveOrder(OrderDto orderDto) {
		Order order = new Order();
		try {
			BeanUtils.copyProperties(orderDto, order);
			order = orderRepo.save(order);
		} catch (Exception e) {
			log.error("[OrderServiceImpl] Adding Order Failed");
			throw new RuntimeException("Adding Order Failed");
		}
		return order;
	}

	@Override
	public List<Order> getOrder() {
		return orderRepo.findAll();
	}

	@Override
	public Order getOrderById(Long id) {
		return orderRepo.findById(id).orElseThrow();
	}

	@Override
	public Order updateorder(Long id, OrderDto orderDto) {
		Order order = new Order();
		try {
			BeanUtils.copyProperties(orderDto, order);
			order = orderRepo.save(order);
		} catch (Exception e) {
			log.error("[OrderServiceImpl] Updating Order Failed");
			throw new RuntimeException("Updating Order Failed");
		}
		return order;
	}

	@Override
	public void deleteOrderById(Long id) {
		orderRepo.deleteById(id);
	}
}
