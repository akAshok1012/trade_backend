package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.CustomerNotificationDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.dto.ManageSalesOrderDto;
import com.tm.app.dto.OrderApprovalDto;
import com.tm.app.dto.OrderIDAndCountDto;
import com.tm.app.dto.OrderIdCustomerNameDto;
import com.tm.app.dto.OrderIdDateTimeDto;
import com.tm.app.dto.OrderItemDto;
import com.tm.app.dto.OrderStatusCustomerNameDto;
import com.tm.app.entity.OrderItem;
import com.tm.app.enums.OrderStatus;

public interface OrderItemService {

	public List<OrderItem> saveOrderItem(OrderItemDto orderItemDto);

	public List<OrderItem> getOrderItems();

	public List<OrderItem> getOrderItemById(Integer orderId);

	public OrderItem updateOrderItem(Long id, OrderItemDto orderItemDto);

	public void deleteOrderItemById(Long id);

	public List<OrderIdCustomerNameDto> getOrderItemByCustomerName(Long id);

	public Page<ManageSalesOrderDto> getOrderStatus(OrderStatus orderStatus, DataFilter dataFilter);

	public List<OrderIDAndCountDto> getOrderIdAndCount();

	public boolean updateOrderApprove(OrderApprovalDto orderApprovalDto);

	public List<OrderIdDateTimeDto> getOrderItemByDateTime();

	public List<OrderItem> getOrderItemBySalesId(Integer salesId);

	public Page<OrderStatusCustomerNameDto> getOrderStatusByUserId(Long id, OrderStatus orderStatus,
			DataFilter dataFilter);

	public void pendingOrderDeleteByOrderId(Integer orderId);

	public List<CustomerNotificationDto> getCustomerNotification(Long id);

	public List<OrderItem> getIsNotificationEnabled(Integer orderId);

}