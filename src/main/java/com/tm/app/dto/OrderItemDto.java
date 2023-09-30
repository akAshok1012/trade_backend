package com.tm.app.dto;

import java.util.List;

import com.tm.app.entity.Customer;
import com.tm.app.entity.ItemDetails;
import com.tm.app.entity.Order;
import com.tm.app.entity.User;
import com.tm.app.enums.OrderStatus;
import com.tm.app.enums.ShipmentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
	private Order order;
	private Customer customer;
	private	List<ItemDetails> itemDetails;
	private String updatedBy;
	private OrderStatus orderStatus;
	private ShipmentStatus shipmentStatus;
	private User userId;
}
