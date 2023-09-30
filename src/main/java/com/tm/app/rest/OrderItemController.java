package com.tm.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrCustomer;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrCustomerOrEmployee;
import com.tm.app.service.OrderItemService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.APIResponseConstants;
import com.tm.app.utils.Response;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Transactional
public class OrderItemController {

	@Autowired
	private OrderItemService orderItemService;

	@PostMapping("/order-item")
	@IsSuperAdminOrAdminOrCustomerOrEmployee
	public APIResponse<?> saveOrderItem(@RequestBody OrderItemDto orderItemDto) {
		log.info("[OrderItemController] saveOrderItem starts ");

		List<OrderItem> orders = null;
		try {
			orders = orderItemService.saveOrderItem(orderItemDto);
		} catch (Exception e) {

			e.printStackTrace();
			log.error("[OrderItemController] saveOrderItem error ", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		log.info("[OrderItemController] saveOrderItem ends ");
		return Response.getSuccessResponse(orders, "Order Placed Successfully", HttpStatus.OK);
	}

	@GetMapping("/orders-items")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> getOrderItems() {
		log.info("[OrderItemController] getOrderItems starts ");

		List<OrderItem> orders = null;
		try {
			orders = orderItemService.getOrderItems();
		} catch (Exception e) {
			log.error("[OrderItemController] getOrderItems error ", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		log.info("[OrderItemController] getOrderItems ends ");
		return Response.getSuccessResponse(orders, "Success", HttpStatus.OK);
	}

	@GetMapping("/order-item")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> getOrderItemById(@RequestParam("orderId") Integer orderId) {
		log.info("[OrderItemController] getOrderItemById starts ");

		List<OrderItem> orders = null;
		try {
			orders = orderItemService.getOrderItemById(orderId);
		} catch (Exception e) {
			log.error("[OrderItemController] getOrderItemById error ", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		log.info("[OrderItemController] getOrderItemById ends ");
		return Response.getSuccessResponse(orders, "Success", HttpStatus.OK);
	}

	@GetMapping("/orders-customer")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> getOrderItemByCustomerName(@RequestParam("id") Long id) {
		log.info("[OrderItemController] getOrderItemByCustomerName starts ");

		List<OrderIdCustomerNameDto> orders = null;
		try {
			orders = orderItemService.getOrderItemByCustomerName(id);
		} catch (Exception e) {
			log.error("[OrderItemController] getOrderItemByCustomerName error ", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		log.info("[OrderItemController] getOrderItemByCustomerName ends ");
		return Response.getSuccessResponse(orders, "success", HttpStatus.OK);
	}

	@GetMapping("/orders-notification")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> getOrderItemByDateTime() {
		log.info("[OrderItemController] getOrderItemByDateTime starts ");

		List<OrderIdDateTimeDto> orders = null;
		try {
			orders = orderItemService.getOrderItemByDateTime();
		} catch (Exception e) {
			log.error("[OrderItemController] getOrderItemByDateTime error ", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		log.info("[OrderItemController] getOrderItemByDateTime ends ");
		return Response.getSuccessResponse(orders, "success", HttpStatus.OK);
	}

	@PutMapping("/order-item/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateOrderItem(@PathVariable Long id, @RequestBody OrderItemDto orderItemDto) {
		log.info("[OrderItemController] updateOrderItem starts ");

		OrderItem orders = null;
		try {
			orders = orderItemService.updateOrderItem(id, orderItemDto);
		} catch (Exception e) {
			log.error("[OrderItemController] updateOrderItem error ", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		log.info("[OrderItemController] updateOrderItem ends ");
		return Response.getSuccessResponse(orders, "Order Updated Successfully", HttpStatus.OK);
	}

	@DeleteMapping("/order-item/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteOrderItemById(@PathVariable("id") Long id) {
		log.info("[OrderItemController] deleteOrderItemById starts ");

		try {
			orderItemService.deleteOrderItemById(id);
		} catch (Exception e) {
			log.error("[OrderItemController] deleteOrderItemById error ", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		log.info("[OrderItemController] deleteOrderItemById ends ");
		return Response.getSuccessResponse(null, "Order Deleted Successfully", HttpStatus.NO_CONTENT);
	}

	@GetMapping("/order-status")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getOrderStatus(@RequestParam("orderStatus") OrderStatus orderStatus,
			@ModelAttribute DataFilter dataFilter) {
		log.info("[OrderItemController] getOrderStatus starts ");

		Page<ManageSalesOrderDto> orders = null;
		try {
			orders = orderItemService.getOrderStatus(orderStatus, dataFilter);
		} catch (Exception e) {
			log.error("[OrderItemController] getOrderStatus error ", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		log.info("[OrderItemController] getOrderStatus ends ");
		return Response.getSuccessResponse(orders, "success", HttpStatus.OK);
	}

	@GetMapping("/order-id-count")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getOrderIdAndCount() {
		log.info("[OrderItemController] getOrderIdAndCount starts ");

		List<OrderIDAndCountDto> orderIDAndStatusDto = null;
		try {
			orderIDAndStatusDto = orderItemService.getOrderIdAndCount();
		} catch (Exception e) {
			log.error("[OrderItemController] getOrderStatus error ", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		log.info("[OrderItemController] getOrderIdAndCount ends ");
		return Response.getSuccessResponse(orderIDAndStatusDto, "success", HttpStatus.OK);
	}

	@PutMapping("/order-item-approval")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateOrderApprove(@RequestBody OrderApprovalDto orderApprovalDto) {
		log.info("[OrderItemController] updateOrderApprove starts ");
		try {
			boolean result = orderItemService.updateOrderApprove(orderApprovalDto);
			if (result) {
				if (orderApprovalDto.getRejectReasonId() != null) {
					return Response.getSuccessResponse(result, "Rejected  Successfully", HttpStatus.OK);
				}
				log.info("[OrderItemController] updateOrderApprove ends ");
				return Response.getSuccessResponse(result, "Order Approved Successfully", HttpStatus.OK);
			}
		} catch (Exception e) {
			log.info("[OrderItemController] updateOrderApprove ends ");
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;

	}

	@GetMapping("/order-item-salesId")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> getOrderItemBySalesId(@RequestParam("salesId") Integer salesId) {
		log.info("[OrderItemController] getOrderItemBySalesId starts ");

		List<OrderItem> orders = null;
		try {
			orders = orderItemService.getOrderItemBySalesId(salesId);
		} catch (Exception e) {
			log.error("[OrderItemController] getOrderItemBySalesId error ", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		log.info("[OrderItemController] getOrderItemBySalesId starts ");
		return Response.getSuccessResponse(orders, "Success", HttpStatus.OK);
	}

	@GetMapping("/order-status-userId")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> getOrderStatusByUserId(@RequestParam("id") Long id, @RequestParam OrderStatus orderStatus,
			@ModelAttribute DataFilter dataFilter) {
		try {
			Page<OrderStatusCustomerNameDto> orderStatusCustomerNameDto = orderItemService.getOrderStatusByUserId(id,
					orderStatus, dataFilter);
			return Response.getSuccessResponse(orderStatusCustomerNameDto, "success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/pending-order-delete")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> pendingOrderDeleteByOrderId(@RequestParam("orderId") Integer orderId) {
		log.info("[OrderItemController] deleteOrderItemById starts ");
		try {
			orderItemService.pendingOrderDeleteByOrderId(orderId);
		} catch (Exception e) {
			log.error("[OrderItemController] deleteOrderItemById error ", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("[OrderItemController] deleteOrderItemById ends ");
		return Response.getSuccessResponse(null, "Order Deleted Successfully", HttpStatus.NO_CONTENT);
	}

	@GetMapping("/customer-notification")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> getCustomerNotification(@RequestParam("id") Long id) {
		try {
			List<CustomerNotificationDto> customerNotificationDto = orderItemService.getCustomerNotification(id);
			return Response.getSuccessResponse(customerNotificationDto, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customer-notification-enabled")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> getIsNotificationEnabled(@RequestParam("orderId") Integer orderId) {
		try {
			List<OrderItem> orderItem = orderItemService.getIsNotificationEnabled(orderId);
			return Response.getSuccessResponse(orderItem, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}