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

import com.tm.app.dto.CustomerOutstandingDto;
import com.tm.app.dto.CustomersPaymentStatusOrderStatusDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.dto.OutstandingPaymentHistoryOrderDto;
import com.tm.app.dto.PaymentCustomerDto;
import com.tm.app.dto.PaymentDto;
import com.tm.app.dto.PaymentOrderIdAndCountDto;
import com.tm.app.dto.PaymentPendingDetailsDto;
import com.tm.app.dto.PaymentSearchByCustomerDto;
import com.tm.app.dto.PaymentStatusDto;
import com.tm.app.dto.PaymentStatusSalesIdDto;
import com.tm.app.entity.Payment;
import com.tm.app.entity.PaymentHistory;
import com.tm.app.enums.PaymentStatus;
import com.tm.app.security.annotations.IsSuperAdmin;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrCustomer;
import com.tm.app.service.PaymentService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@PostMapping("/payment")
	@IsSuperAdminOrAdmin
	public APIResponse<?> savePayment(@RequestBody PaymentDto paymentDto) {
		try {
			Payment payment = paymentService.savePayment(paymentDto);
			return Response.getSuccessResponse(payment, "Payment Saved Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/payments")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getPayments(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<Payment> payments = paymentService.getPayments(dataFilter);
			return Response.getSuccessResponse(payments, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/payment/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getPaymentById(@PathVariable("id") Long id) {
		try {
			Payment payment = paymentService.getPaymentById(id);
			return Response.getSuccessResponse(payment, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/payment")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updatePayment(@RequestBody PaymentDto paymentDto) {
		try {
			Payment payment = paymentService.updatePayment(paymentDto);
			return Response.getSuccessResponse(payment, "Payment Paid Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/payment/{id}")
	@IsSuperAdmin
	public APIResponse<?> deletePaymentById(@PathVariable("id") Long id) {
		try {
			paymentService.deletePaymentById(id);
			return Response.getSuccessResponse(null, "Payment Deleted Successfully", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customers-status")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getCustomersStatus() {
		try {
			List<CustomersPaymentStatusOrderStatusDto> customer = paymentService.getCustomersStatus();
			return Response.getSuccessResponse(customer, "success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/payment-status")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getPaymentStatus(@RequestParam("paymentStatus") PaymentStatus paymentStatus,
			@ModelAttribute DataFilter dataFilter) {
		try {
			Page<PaymentStatusDto> payments = paymentService.getPaymentStatus(paymentStatus, dataFilter);
			return Response.getSuccessResponse(payments, "success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/payment-status-unpaid")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getUnpaidPaymentStatus() {
		try {
			List<Payment> paymentStatusDto = paymentService.getUnpaidPaymentStatus();
			return Response.getSuccessResponse(paymentStatusDto, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/payment-orderId-count")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getPaymentCountAndOrderId() {
		try {
			List<PaymentOrderIdAndCountDto> paymentStatusDto = paymentService.getPaymentCountAndOrderId();
			return Response.getSuccessResponse(paymentStatusDto, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/payment-pending-details")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getPaymentPendingDetails() {
		try {
			List<PaymentPendingDetailsDto> paymentPendingDetailsDtos = paymentService.getPaymentPendingDetails();
			return Response.getSuccessResponse(paymentPendingDetailsDtos, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/payment-orderId")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getPaymentOrderId(@RequestParam("order") Integer order) {
		try {
			List<Payment> payment = paymentService.getPaymentOrderId(order);
			return Response.getSuccessResponse(payment, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/payment-salesId")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getPaymentBySalesId(@RequestParam("salesId") Integer salesId) {
		try {
			List<Payment> payment = paymentService.getPaymentBySalesId(salesId);
			return Response.getSuccessResponse(payment, "success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/payment-customer")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getPaymentByCustomer(@RequestParam("name") String name,
			@ModelAttribute DataFilter dataFilter) {
		try {
			Page<Payment> payment = paymentService.getPaymentByCustomer(name, dataFilter);
			return Response.getSuccessResponse(payment, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/payment-customerId")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> getPaymentByCustomerId(@RequestParam("id") Long customerId,
			@ModelAttribute DataFilter dataFilter) {
		try {
			Page<Payment> payment = paymentService.getPaymentByCustomerId(customerId, dataFilter);
			return Response.getSuccessResponse(payment, "success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/payment-search-by-customer")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getCustomerPaymentBySearch(@RequestParam("search") String search) {
		try {
			List<PaymentSearchByCustomerDto> paymentSearchByCustomerDto = paymentService
					.getCustomerPaymentBySearch(search);
			return Response.getSuccessResponse(paymentSearchByCustomerDto, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/payment-userId")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> getPaymentUserId(@RequestParam("id") Long id,
			@RequestParam("PaymentStatus") PaymentStatus paymentStatus, @ModelAttribute DataFilter dataFilter) {
		try {
			Page<PaymentCustomerDto> paymentCustomerDto = paymentService.getPaymentUserId(id, paymentStatus,
					dataFilter);
			return Response.getSuccessResponse(paymentCustomerDto, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customer-outstanding")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getCustomerOutstanding(@RequestParam("id") Long id) {
		try {
			Float payment = paymentService.getCustomerOutstanding(id);
			return Response.getSuccessResponse(payment, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customer-outstanding-userid")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> getCustomerOutstandingByUserId(@RequestParam("id") Long id) {
		try {
			CustomerOutstandingDto payment = paymentService.getCustomerOutstandingByUserId(id);
			return Response.getSuccessResponse(payment, "success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/view-payment")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> getViewPayment(@RequestParam("salesId") Integer salesId) {
		try {
			List<PaymentHistory> paymentHistory = paymentService.getViewPayment(salesId);
			return Response.getSuccessResponse(paymentHistory, "success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/payment-status-salesid")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> getPaymentStatusSalesId(@RequestParam PaymentStatus paymentStatus,@RequestParam(value="id",required = false) Long id,
			@RequestParam(value="salesId",required = false) Integer salesId, @ModelAttribute DataFilter dataFilter) {
		try {
			Page<PaymentStatusSalesIdDto> paymentStatusSalesIdDto = paymentService
					.getPaymentStatusSalesId(paymentStatus, id, salesId, dataFilter);
			return Response.getSuccessResponse(paymentStatusSalesIdDto, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/payments-bySalesId")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getPaymentsBySalesId(@RequestParam(value="salesId",required = false) Integer salesId, @ModelAttribute DataFilter dataFilter) {
		try {
			Page<Payment> payments = paymentService.getPaymentsBySalesId(salesId,dataFilter);
			return Response.getSuccessResponse(payments, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/outstanding-order-payment")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getOutstandingOrderPayment(@RequestParam("id") Long id) {
		try {
			List<OutstandingPaymentHistoryOrderDto> outstandingPaymentHistoryOrderDto = paymentService.getOutstandingOrderPayment(id);
			return Response.getSuccessResponse(outstandingPaymentHistoryOrderDto, "success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}