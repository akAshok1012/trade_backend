package com.tm.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tm.app.entity.PaymentHistory;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.service.PaymentHistoryService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j
public class PaymentHistoryController {

	@Autowired
	private PaymentHistoryService paymentHistoryService;

	@GetMapping("/payment-history")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getPaymentHistory(@RequestParam("id") Long id) {
		try {
			log.info("Starts");
			List<PaymentHistory> paymentHistory = paymentHistoryService.getPaymentHistoryByCustomer(id);
			return Response.getSuccessResponse(paymentHistory, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
