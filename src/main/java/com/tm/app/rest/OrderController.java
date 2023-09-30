package com.tm.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tm.app.dto.OrderDto;
import com.tm.app.entity.Order;
import com.tm.app.service.OrderService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.APIResponseConstants;
import com.tm.app.utils.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping("/order")
	public APIResponse<?> saveOrder(@RequestBody OrderDto orderDto) {
		log.info("OrderController] Create Order Starts ");
		try {
			Order order = orderService.saveOrder(orderDto);
			return Response.getSuccessResponse(order, APIResponseConstants.CREATION_SUCCESS_MESSAGE,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("OrderController] Create Order failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/order")
	public APIResponse<?> getOrder() {
		log.info("[OrderController] Get Order Starts ");
		try {
			List<Order>  order = orderService.getOrder();
			return Response.getSuccessResponse(order,"Success",HttpStatus.OK);
		} catch (Exception e) {
			log.error("[OrderController] Get Order Failed ", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/order/{id}")
	public APIResponse<?> getOrderById(@PathVariable("id") Long id) {
		log.info(" OrderController] GetOrderById Starts ");
		try {
			Order orderDto =  orderService.getOrderById(id);
			return Response.getSuccessResponse( orderDto,"Success",HttpStatus.OK);
		} catch (Exception e) {
			log.error("[OrderController] GetOrderById Failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/order/{id}")
	public APIResponse<?> updateOrder(@PathVariable Long id, @RequestBody  OrderDto orderDto) {
		log.info("[OrderController] Updating Order Starts ");
		try {
			Order order =orderService.updateorder(id, orderDto);
			return Response.getSuccessResponse(order, APIResponseConstants.UPDATED_SUCCESS_MESSAGE,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[OrderController] Updating  Order Failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/order/{id}")
	public APIResponse<?> deleteOrderById(@PathVariable("id") Long id) {
		log.info("OrderController] Deleting Order Starts ");
		try {
			orderService.deleteOrderById(id);
			return Response.getSuccessResponse(null, APIResponseConstants.DELETE_RESPONSE_MESSAGE,HttpStatus.OK);
		} catch (Exception e) {
			log.error("[IrdertController] deleteOrderById failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	
}
