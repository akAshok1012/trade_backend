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
import org.springframework.web.bind.annotation.RestController;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.PaymentTypeDto;
import com.tm.app.entity.PaymentType;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.service.PaymentTypeService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PaymentTypeController {

	@Autowired
	private PaymentTypeService paymentTypeService;

	@PostMapping("/payment-type")
	@IsSuperAdminOrAdmin
	public APIResponse<?> savePaymentType(@RequestBody PaymentTypeDto paymentTypeDto) {
		try {
			PaymentType paymentTypes = paymentTypeService.savePaymentType(paymentTypeDto);
			return Response.getSuccessResponse(paymentTypes, "PaymentType Added Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/payment-types")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getPaymentTypes() {
		try {
			List<PaymentType> paymentTypes = paymentTypeService.getPaymentTypes();
			return Response.getSuccessResponse(paymentTypes, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/payment-type/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getPaymentTypeById(@PathVariable("id") Long id) {
		try {
			PaymentType paymentTypes = paymentTypeService.getPaymentTypeById(id);
			return Response.getSuccessResponse(paymentTypes, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/payment-type/{id}")
	public APIResponse<?> updatePaymentType(@PathVariable Long id, @RequestBody PaymentTypeDto paymentTypeDto) {
		try {
			PaymentType paymentTypes = paymentTypeService.updatePaymentType(id, paymentTypeDto);
			return Response.getSuccessResponse(paymentTypes, "PaymentType Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/payment-type/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deletePaymentType(@PathVariable("id") Long id) {
		try {
			paymentTypeService.deletePaymentType(id);
			return Response.getSuccessResponse(null, "PaymentType Deleted Successfully", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/payment-type-list")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getPaymentTypeList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<PaymentType> paymentTypes = paymentTypeService.getPaymentTypeList(dataFilter);
			return Response.getSuccessResponse(paymentTypes, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}