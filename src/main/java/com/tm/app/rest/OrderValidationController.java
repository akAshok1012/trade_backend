package com.tm.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tm.app.dto.OrderValidationDto;
import com.tm.app.entity.OrderValidation;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrCustomer;
import com.tm.app.service.OrderValidationService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class OrderValidationController {

	@Autowired
	private OrderValidationService orderValidationService;

	@PostMapping("/order-validation")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> saveOrderValidation(@RequestBody OrderValidationDto orderValidationDto) {
		try {
			OrderValidation orderValidation = orderValidationService.saveOrderValidation(orderValidationDto);
			return Response.getSuccessResponse(orderValidation, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/order-validations")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> getOrderValidations() {
		try {
			List<OrderValidation> orderValidations = orderValidationService.getOrderValidations();
			return Response.getSuccessResponse(orderValidations, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/order-validation/{id}")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> getOrderValidationById(@PathVariable("id") Long id) {
		try {
			OrderValidation orderValidation = orderValidationService.getOrderValidationById(id);
			return Response.getSuccessResponse(orderValidation, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/order-validation/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteOrderValidationById(@PathVariable("id") Long id) {
		try {
			orderValidationService.deleteOrderValidationById(id);
			return Response.getSuccessResponse(null, "success", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/order-validation/{id}")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> updateOrderValidation(@PathVariable Long id,
			@RequestBody OrderValidationDto orderValidationDto) {
		try {
			OrderValidation orderValidation = orderValidationService.updateOrderValidation(id, orderValidationDto);
			return Response.getSuccessResponse(orderValidation, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}