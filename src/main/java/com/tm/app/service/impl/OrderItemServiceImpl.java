package com.tm.app.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.CustomerNotificationDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.dto.ManageSalesOrderDto;
import com.tm.app.dto.OrderApprovalDto;
import com.tm.app.dto.OrderIDAndCountDto;
import com.tm.app.dto.OrderIdCustomerNameDto;
import com.tm.app.dto.OrderIdDateTimeDto;
import com.tm.app.dto.OrderItemDetailsDto;
import com.tm.app.dto.OrderItemDto;
import com.tm.app.dto.OrderStatusCustomerNameDto;
import com.tm.app.entity.Customer;
import com.tm.app.entity.ItemDetails;
import com.tm.app.entity.Notification;
import com.tm.app.entity.Order;
import com.tm.app.entity.OrderItem;
import com.tm.app.entity.Payment;
import com.tm.app.entity.SalesOrder;
import com.tm.app.entity.Shipment;
import com.tm.app.entity.User;
import com.tm.app.enums.NotificationStatus;
import com.tm.app.enums.OrderStatus;
import com.tm.app.enums.PaymentStatus;
import com.tm.app.enums.SalesStatus;
import com.tm.app.enums.ShipmentStatus;
import com.tm.app.repo.CustomerRepo;
import com.tm.app.repo.NotificationRepo;
import com.tm.app.repo.OrderItemRepo;
import com.tm.app.repo.OrderRepo;
import com.tm.app.repo.PaymentRepo;
import com.tm.app.repo.SalesOrderRepo;
import com.tm.app.repo.ShipmentRepo;
import com.tm.app.repo.UserRepository;
import com.tm.app.service.OrderItemService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired
	private OrderItemRepo orderItemRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private SalesOrderRepo salesOrderRepo;

	@Autowired
	private PaymentRepo paymentRepo;

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ShipmentRepo shipmentRepo;

	@Autowired
	private NotificationRepo notificationRepo;

	@Override
	public List<OrderItem> saveOrderItem(OrderItemDto orderItemDto) {
		log.info("[OrderItemServiceImpl] saveOrderItem starts ");
		List<OrderItem> orderItemsList = new ArrayList<>();
		Order order = new Order();
		try {
			Integer orderNumber = orderIdGentration();
			for (ItemDetails itemDetails : orderItemDto.getItemDetails()) {
				order.setOrderId(orderNumber);
				if (orderItemDto.getUserId() != null) {
					User user = userRepository.findById(orderItemDto.getUserId().getId()).orElseThrow();
					Customer customer = customerRepo.findById(user.getUserId()).orElseThrow();
					order.setCustomer(customer);
				} else {
					order.setCustomer(orderItemDto.getCustomer());
				}
				order.setOrderStatus(orderItemDto.getOrderStatus());
				order.setUpdatedBy(orderItemDto.getUpdatedBy());
				order = orderRepo.save(order);
				OrderItem orderItem = new OrderItem();
				orderItem.setOrder(order);
				orderItem.setItemMaster(itemDetails.getItemMaster());
				orderItem.setUnitOfMeasure(itemDetails.getUnitOfMeasure());
				orderItem.setUnitPrice(itemDetails.getItemMaster().getFixedPrice());
				orderItem.setOrderedQuantity(itemDetails.getOrderedQuantity());
				orderItem.setTotalAmount(
						(itemDetails.getUnitOfMeasure().getUnitWeight() * itemDetails.getOrderedQuantity())
								* orderItem.getUnitPrice());
				orderItem.setUpdatedBy(orderItemDto.getUpdatedBy());
				orderItemsList.add(orderItem);
				log.info("[ORDER] orderItems placed with orderId[{}]", orderItem.getOrder().getOrderId());
			}
			orderItemsList = orderItemRepo.saveAll(orderItemsList);
			log.info("[ORDER] order placed with orderId[{}]", order.getOrderId());

			// insert in Notification table
			updateOrderPlacedNotification(order);

		} catch (Exception e) {
			log.error("[OrderItemServiceImpl] saveOrderItem failed", e);
			throw new RuntimeException("Order placing failed");
		}
		log.info("[OrderItemServiceImpl] saveOrderItem ends ");
		return orderItemsList;
	}

	/**
	 * Insert in Notification table
	 * 
	 * @param order
	 */
	private void updateOrderPlacedNotification(Order order) {
		Notification notification = new Notification();
		notification.setPhoneNumber(order.getCustomer().getPhoneNumber());
		notification.setMessage("Hi " + order.getCustomer().getName() + ", Your order " + order.getOrderId()
				+ " has been placed successfully!. We'll update you soon. Thanks, [KPR & Team]");
		notification.setTitle("Order Placed");
		notification.setUpdatedBy(order.getUpdatedBy());
		try {
			notification.setIsSend(true);
			notification.setNotificationStatus(NotificationStatus.PENDING);
		} catch (Exception e) {
			notification.setIsSend(false);
			notification.setNotificationStatus(NotificationStatus.FAILED);
		}
		notification = notificationRepo.save(notification);
	}

	/**
	 * check orderNumber exists
	 * 
	 * @return
	 */
	private Integer orderIdGentration() {
		Integer orderId = RandomUtils.nextInt(100000, 999999);
		if (orderRepo.existsByOrderId(orderId)) {
			return orderIdGentration();
		}
		return orderId;
	}

	@Override
	public List<OrderItem> getOrderItems() {
		log.info("[OrderItemServiceImpl] getOrderItems starts ");

		List<OrderItem> orderItems = null;
		try {
			orderItems = orderItemRepo.findAll();
		} catch (Exception e) {
			log.error("[OrderItemServiceImpl] getOrderItems failed", e);
			throw new RuntimeException("GetOrderItems failed ");
		}

		log.info("[OrderItemServiceImpl] getOrderItems ends ");
		return orderItems;
	}

	@Override
	public List<OrderItem> getOrderItemById(Integer orderId) {
		log.info("[OrderItemServiceImpl] getOrderItemById starts ");

		List<OrderItem> orderItems = null;
		try {
			Order order = orderRepo.findByOrderId(orderId);
			orderItems = orderItemRepo.findByOrder(order);
		} catch (Exception e) {
			log.error("[OrderItemServiceImpl] getOrderItems failed", e);
			throw new RuntimeException("GetOrderItemById failed ");
		}

		log.info("[OrderItemServiceImpl] getOrderItemById ends ");
		return orderItems;
	}

	@Override
	public OrderItem updateOrderItem(Long id, OrderItemDto orderItemDto) {
		OrderItem orders = orderItemRepo.findById(id).orElseThrow();
		orders.setUpdatedBy(orderItemDto.getUpdatedBy());
		orders = orderItemRepo.save(orders);
		return orders;
	}

	@Override
	public void deleteOrderItemById(Long id) {
		try {
			orderItemRepo.deleteById(id);
		} catch (Exception e) {
			log.error("[ORDER] order deleting failed", e);
			throw new RuntimeException("Deleting order failed");
		}
	}

	@Override
	public List<OrderIdCustomerNameDto> getOrderItemByCustomerName(Long id) {
		User user = userRepository.findById(id).orElseThrow();
		Customer customer = customerRepo.findById(user.getUserId()).orElseThrow();
		return orderRepo.findByCustomer(customer);
	}

	@Override
	public List<OrderIdDateTimeDto> getOrderItemByDateTime() {
		return orderRepo.getOrderIdDate();
	}

	@Override
	public Page<ManageSalesOrderDto> getOrderStatus(OrderStatus orderStatus, DataFilter dataFilter) {
		log.info("[Order] Get Manage Sales Order");
		return orderRepo.findByOrderStatusAndSearch(orderStatus, dataFilter.getSearch(),
				PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
						Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}

	@Override
	public List<OrderIDAndCountDto> getOrderIdAndCount() {
		return orderRepo.getOrderIdAndCount();
	}

	@Override
	public boolean updateOrderApprove(OrderApprovalDto orderApprovalDto) {
		// 1. Update orderStatus and Reject Reason in t_order by order_id
		// 2. Update price in t_order_items when orderItem map has value
		// 3. Insert into t_sales_order table
		// 4. Insert into t_payment
		try {
			// update status in orderItem
			Order order = updateOrder(orderApprovalDto);
			List<OrderItem> orderItems = orderItemRepo.findByOrder(order);
			List<Long> getActualItemList = orderApprovalDto.getItemDetails().stream().map(r -> r.getId()).toList();
			// delete order item that removed by approver
			if (orderItems.size() != getActualItemList.size()) {
				orderItems.stream().forEach(r -> {
					if (!getActualItemList.contains(r.getId())) {
						orderItemRepo.deleteById(r.getId());
					}
				});
				orderItems = orderItemRepo.findByOrder(order);
			}
			// update orderItemPrice
			orderItems = updateOrderItemsPrice(orderApprovalDto, orderItems);
			// saves sales Table and Payment Table
			if (orderApprovalDto.getOrderStatus().equals(OrderStatus.APPROVED)) {
				SalesOrder salesOrder = saveSalesOrder(orderApprovalDto, order.getCustomer());
				float paymentAmount = getPaymentAmount(orderItems);
				savePaymentDetails(orderApprovalDto, salesOrder.getSalesId(), order.getCustomer(), paymentAmount);
				saveShipment(salesOrder.getSalesId(), orderItems);
			}

			// insert in Notification table
			updateOrderApprovedNotification(order);
			return true;
		} catch (Exception e) {
			log.error("error on updating order approved {}", e);
			return false;
		}
	}

	private void updateOrderApprovedNotification(Order order) {
		Notification notification = new Notification();
		notification.setPhoneNumber(order.getCustomer().getPhoneNumber());
		notification.setMessage("Hello " + order.getCustomer().getName() + ", Great News! Your Order "
				+ order.getOrderId()
				+ " has been Approved Successfully!. We'll keep you posted on its status. Thanks, [KPR & Team]");
		notification.setTitle("Order Approved");
		notification.setUpdatedBy(order.getUpdatedBy());
		try {
			notification.setIsSend(true);
			notification.setNotificationStatus(NotificationStatus.PENDING);
		} catch (Exception e) {
			notification.setIsSend(false);
			notification.setNotificationStatus(NotificationStatus.FAILED);
		}
		notification = notificationRepo.save(notification);
	}

	/**
	 * Update Shipment
	 * 
	 * @param salesId
	 * @param orderItems
	 */
	private void saveShipment(Integer salesId, List<OrderItem> orderItems) {
		List<Shipment> shipmentList = new ArrayList<>();
		for (OrderItem orderItem : orderItems) {
			Shipment shipmentDetails = new Shipment();
			shipmentDetails.setSalesId(salesId);
			shipmentDetails.setUpdatedBy(orderItem.getUpdatedBy());
			shipmentDetails.setOrderItem(orderItem);
			shipmentDetails.setShippedQuantity(0);
			shipmentDetails.setBalanceQuantity(orderItem.getOrderedQuantity());
			shipmentDetails.setShipmentStatus(ShipmentStatus.PENDING);
			shipmentList.add(shipmentDetails);
		}
		shipmentRepo.saveAll(shipmentList);
	}

	/**
	 * update Payment Amount
	 * 
	 * @param orderItems
	 * @return paymentAmount
	 */
	private float getPaymentAmount(List<OrderItem> orderItems) {
		float paymentAmount = 0;
		for (OrderItem orderItem : orderItems) {
			paymentAmount += orderItem.getTotalAmount();
		}
		return paymentAmount;
	}

	/**
	 * 
	 * @param orderApprovalDto
	 * @param orderItems
	 * @return
	 */
	private List<OrderItem> updateOrderItemsPrice(OrderApprovalDto orderApprovalDto, List<OrderItem> orderItems) {
		List<OrderItemDetailsDto> itemDetails = orderApprovalDto.getItemDetails();
		Map<Long, Float> itemMap = itemDetails.stream()
				.collect(Collectors.toMap(r -> r.getId(), OrderItemDetailsDto::getUnitPrice));
		orderItems.stream().forEach(item -> {
			if (itemMap.containsKey(item.getId()))
				item.setUnitPrice(itemMap.get(item.getId()));
			item.setTotalAmount(
					(item.getUnitOfMeasure().getUnitWeight() * item.getOrderedQuantity()) * itemMap.get(item.getId()));
		});
		return orderItemRepo.saveAll(orderItems);
	}

	/**
	 * 
	 * @param orderApprovalDto
	 * @return
	 */
	private Order updateOrder(OrderApprovalDto orderApprovalDto) {
		Order order = orderRepo.findByOrderId(orderApprovalDto.getOrderId());

		try {
			if (Objects.nonNull(orderApprovalDto.getOrderStatus()))
				order.setOrderStatus(orderApprovalDto.getOrderStatus());
			if (Objects.nonNull(orderApprovalDto.getRejectReasonId())) {
				order.setRejectReason(orderApprovalDto.getRejectReasonId());
			}
			order.setIsNotificationEnabled(false);
			order.setUpdatedBy(orderApprovalDto.getUpdatedBy());
			order = orderRepo.save(order);
			log.info("[ORDER] approve/update order with orderId [{}]", order.getOrderId());
		} catch (Exception e) {
			log.error("[ORDER] approve/update order  failed", e);
			throw new RuntimeException("Approve Order failed");
		}
		return order;
	}

	/**
	 * @param customer
	 * @param orderItemList
	 * @param salesOrder
	 */
	private void savePaymentDetails(OrderApprovalDto orderApprovalDto, Integer salesId, Customer customer,
			float paymentAmount) {
		Payment payment = new Payment();
		try {
			payment.setSalesId(salesId);
			payment.setCustomer(customer);
			payment.setPaymentStatus(PaymentStatus.UNPAID);
			payment.setTotalOrderAmount(paymentAmount);
			payment.setBalanceAmount(paymentAmount);
			payment.setTotalPaidAmount(0F);
			payment.setPaymentDate(null);
			payment.setSalesOrderDate(new Date(System.currentTimeMillis()));
			payment.setUpdatedBy(orderApprovalDto.getUpdatedBy());
			payment.setDeliveryPayableAmount(0F);
			payment = paymentRepo.save(payment);
			log.info("[PAYMENT] adding paymentDetails with salesId [{}]", payment.getSalesId());
		} catch (Exception e) {
			log.error("[PAYMENT] adding payment  failed", e);
			throw new RuntimeException("Adding payment failed");
		}
	}

	/**
	 * @param orderApprovalDto.getOrderId()
	 * @param customer
	 * @return
	 */
	private SalesOrder saveSalesOrder(OrderApprovalDto orderApprovalDto, Customer customer) {
		SalesOrder salesOrder = new SalesOrder();
		try {
			Integer salesId = salesIdGeneration();
			salesOrder.setSalesId(salesId);
			salesOrder.setOrder(orderApprovalDto.getOrderId());
			salesOrder.setSalesStatus(SalesStatus.PROCESSING);
			salesOrder.setCustomer(customer);
			salesOrder.setUpdatedBy(orderApprovalDto.getUpdatedBy());
			salesOrder = salesOrderRepo.save(salesOrder);
			log.info("[SALESORDER] adding paymentDetails with orderId [{}]", salesOrder.getOrder());
		} catch (Exception e) {
			log.error("[SALESORDER] adding salesOrder failed", e);
			throw new RuntimeException("Adding salesOrder failed");
		}
		return salesOrder;
	}

	/**
	 * check exists salesId
	 * 
	 * @return
	 */
	private Integer salesIdGeneration() {
		Integer salesId = RandomUtils.nextInt(200000, 999999);
		if (salesOrderRepo.existsBySalesId(salesId)) {
			return salesIdGeneration();
		}
		return salesId;
	}

	@Override
	public List<OrderItem> getOrderItemBySalesId(Integer salesId) {
		SalesOrder salesOrder = salesOrderRepo.findBySalesId(salesId);
		Order order = orderRepo.findByOrderId(salesOrder.getOrder());
		return orderItemRepo.findByOrder(order);
	}

	@Override
	public Page<OrderStatusCustomerNameDto> getOrderStatusByUserId(Long id, OrderStatus orderStatus,
			DataFilter dataFilter) {
		return orderItemRepo.getOrderStatusByUserId(id, orderStatus, PageRequest.of(dataFilter.getPage(),
				dataFilter.getSize(), Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}

	@Override
	public void pendingOrderDeleteByOrderId(Integer orderId) {
		Order order = orderRepo.findByOrderIdAndOrderStatus(orderId, OrderStatus.PENDING);
		if (Objects.nonNull(order)) {
			orderItemRepo.deleteAllByOrder(order);
			orderRepo.deleteByOrderId(order.getOrderId());
		}
	}

	@Override
	public List<CustomerNotificationDto> getCustomerNotification(Long id) {
		return orderRepo.getCustomerNotification(id);
	}

	@Override
	public List<OrderItem> getIsNotificationEnabled(Integer orderId) {
		Order order = orderRepo.findByOrderId(orderId);
		order.setIsNotificationEnabled(true);
		order = orderRepo.save(order);
		return orderItemRepo.findByOrder(order);
	}
}