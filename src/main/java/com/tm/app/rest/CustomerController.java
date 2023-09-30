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

import com.tm.app.dto.CustomerDto;
import com.tm.app.dto.CustomerIdNameDto;
import com.tm.app.dto.CustomerUserDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.entity.Customer;
import com.tm.app.enums.CustomerType;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrCustomer;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrEmployee;
import com.tm.app.service.CustomerService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.APIResponseConstants;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping("/customer")
	@IsSuperAdminOrAdmin
	public APIResponse<?> saveCustomer(@RequestBody CustomerDto customerDto) {
		try {
			Customer customers = customerService.saveCustomer(customerDto);
			return Response.getSuccessResponse(customers,
					String.format(APIResponseConstants.CREATION_SUCCESS_MESSAGE, customers.getName()), HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customers")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getCustomers() {
		try {
			List<Customer> customers = customerService.getCustomers();
			return Response.getSuccessResponse(customers, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customer/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getCustomerById(@PathVariable("id") Long id) {
		try {
			Customer customers = customerService.getCustomerById(id);
			return Response.getSuccessResponse(customers, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/customer/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
		try {
			Customer customers = customerService.updateCustomer(id, customerDto);
			return Response.getSuccessResponse(customers,
					String.format(APIResponseConstants.UPDATED_SUCCESS_MESSAGE, customers.getName()), HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/customer/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteCustomerById(@PathVariable("id") Long id) {
		try {
			customerService.deleteCustomerById(id);
			return Response.getSuccessResponse(null, APIResponseConstants.DELETE_RESPONSE_MESSAGE,
					HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customer-name")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getCustomerIdName() {
		try {
			List<CustomerIdNameDto> customers = customerService.getCustomerIdName();
			return Response.getSuccessResponse(customers, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customer-list")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getCustomerList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<Customer> customers = customerService.getCustomerList(dataFilter);
			return Response.getSuccessResponse(customers, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customer-user")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> getCustomerUser(@RequestParam("id") Long id) {
		try {
			Customer customerUserDto = customerService.getCustomerUserId(id);
			return Response.getSuccessResponse(customerUserDto, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/customer-user")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> updateCustomerUser(@RequestParam("id") Long id,
			@RequestBody CustomerUserDto customerUserDto) {
		try {
			Customer customer = customerService.updateCustomerUser(id, customerUserDto);
			return Response.getSuccessResponse(customer, "Customer profile Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customer-type-list")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getCustomerTypeList(
			@RequestParam(value = "customerType", required = false) CustomerType customerType,
			@ModelAttribute DataFilter dataFilter) {
		try {
			Page<Customer> customers = customerService.getCustomerTypeList(customerType, dataFilter);
			return Response.getSuccessResponse(customers, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}