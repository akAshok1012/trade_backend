package com.tm.app.service;

import java.util.List;

import com.tm.app.dto.OrderDto;
import com.tm.app.entity.Order;

public interface OrderService {

	public Order saveOrder(OrderDto orderDto);

	public List<Order> getOrder();

	public Order getOrderById(Long id);

	public Order updateorder(Long id, OrderDto orderDto);

	public void deleteOrderById(Long id);

	

}
