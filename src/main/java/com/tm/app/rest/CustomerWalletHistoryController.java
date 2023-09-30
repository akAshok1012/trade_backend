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

import com.tm.app.dto.CustomerWalletHistoryDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.entity.CustomerWalletHistory;
import com.tm.app.security.annotations.IsSuperAdmin;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrCustomer;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrCustomerOrEmployee;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrEmployee;
import com.tm.app.service.CustomerWalletHistoryService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.APIResponseConstants;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CustomerWalletHistoryController {

	@Autowired
	private CustomerWalletHistoryService customerWalletHistoryService;

	@PostMapping("/customer-wallet-history")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> saveCustomerWalletHistory(@RequestBody CustomerWalletHistoryDto customerWalletHistoryDto) {
		try {
			CustomerWalletHistory customerWalletHistory = customerWalletHistoryService
					.saveCustomerWalletHistory(customerWalletHistoryDto);
			return Response.getSuccessResponse(customerWalletHistory, APIResponseConstants.CREATION_SUCCESS_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customer-wallet-historys")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getCustomerWalletHistorys() {
		try {
			List<CustomerWalletHistory> customerWalletHistory = customerWalletHistoryService
					.getCustomerWalletHistorys();
			return Response.getSuccessResponse(customerWalletHistory, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customer-wallet-history/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getCustomerWalletHistoryById(@PathVariable("id") Long id) {
		try {
			CustomerWalletHistory customerWalletHistory = customerWalletHistoryService.getCustomerWalletHistoryById(id);
			return Response.getSuccessResponse(customerWalletHistory, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/customer-wallet-history/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> updateCustomerWalletHistory(@PathVariable Long id,
			@RequestBody CustomerWalletHistoryDto customerWalletHistoryDto) {
		try {
			CustomerWalletHistory customerWalletHistory = customerWalletHistoryService.updateCustomerWalletHistory(id,
					customerWalletHistoryDto);
			return Response.getSuccessResponse(customerWalletHistory, APIResponseConstants.UPDATED_SUCCESS_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/customer-wallet-history/{id}")
	@IsSuperAdmin
	public APIResponse<?> deleteCustomerWalletHistoryById(@PathVariable("id") Long id) {
		try {
			customerWalletHistoryService.deleteCustomerWalletHistoryById(id);
			return Response.getSuccessResponse(null, APIResponseConstants.DELETE_RESPONSE_MESSAGE,
					HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customer-wallet-history-by-walletId")
	@IsSuperAdminOrAdminOrCustomerOrEmployee
	public APIResponse<?> getCustomerWalletHistoryByWalletId(@RequestParam("walletId") Long walletId, @ModelAttribute DataFilter dataFilter,@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate) {
		try {
			Page<CustomerWalletHistory> customerWalletHistory = customerWalletHistoryService
					.getCustomerWalletHistoryByWalletId(walletId,dataFilter,fromDate,toDate);
			return Response.getSuccessResponse(customerWalletHistory, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customer-wallet-history-list")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> getCustomerWalletHistory(@ModelAttribute DataFilter dataFilter,
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate) {
		try {
			Page<CustomerWalletHistory> customerWalletHistory = customerWalletHistoryService
					.getCustomerWalletHistory(dataFilter, id, fromDate, toDate);
			return Response.getSuccessResponse(customerWalletHistory, "Customer Wallet History Datas", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
