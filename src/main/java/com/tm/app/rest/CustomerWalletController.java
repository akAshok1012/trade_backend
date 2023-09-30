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

import com.tm.app.dto.CustomerWalletDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.entity.CustomerWallet;
import com.tm.app.enums.TransactionType;
import com.tm.app.security.annotations.IsSuperAdmin;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrEmployee;
import com.tm.app.service.CustomerWalletService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.APIResponseConstants;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CustomerWalletController {

	@Autowired
	private CustomerWalletService customerWalletService;

	@PostMapping("/customer-wallet")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> saveCustomerWallet(@RequestBody CustomerWalletDto customerWalletDto) {
		try {
			CustomerWallet customerWallet = customerWalletService.saveCustomerWallet(customerWalletDto);
			return Response.getSuccessResponse(customerWallet, APIResponseConstants.CREATION_SUCCESS_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customer-wallets")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getCustomerWallets() {
		try {
			List<CustomerWallet> customerWallet = customerWalletService.getCustomerWallets();
			return Response.getSuccessResponse(customerWallet, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customer-wallet/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getCustomerWalletById(@PathVariable("id") Long id) {
		try {
			CustomerWallet customerWallet = customerWalletService.getCustomerWalletById(id);
			return Response.getSuccessResponse(customerWallet, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/customer-wallet/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> updateCustomerWallet(@PathVariable Long id,
			@RequestBody CustomerWalletDto customerWalletDto) {
		try {
			CustomerWallet customerWallet = customerWalletService.updateCustomerWallet(id, customerWalletDto,
					TransactionType.CREDIT);
			return Response.getSuccessResponse(customerWallet,String.format(APIResponseConstants.UPDATED_SUCCESS_MESSAGE, customerWallet.getCustomer().getName()),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/customer-wallet/{id}")
	@IsSuperAdmin
	public APIResponse<?> deleteCustomerWalletById(@PathVariable("id") Long id) {
		try {
			customerWalletService.deleteCustomerWalletById(id);
			return Response.getSuccessResponse(null, APIResponseConstants.DELETE_RESPONSE_MESSAGE,
					HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customer-wallet-list")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getCustomerWalletList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<CustomerWallet> customerWallet = customerWalletService.getCustomerWalletList(dataFilter);
			return Response.getSuccessResponse(customerWallet, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
