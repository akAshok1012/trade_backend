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

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.EmployeeContractPaymentDto;
import com.tm.app.entity.EmployeeContractPayment;
import com.tm.app.entity.EmployeeContractPaymentHistory;
import com.tm.app.enums.ContractStatus;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.service.EmployeeContractPaymentService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.APIResponseConstants;
import com.tm.app.utils.Response;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Transactional
public class EmployeeContractPaymentController {

	@Autowired
	private EmployeeContractPaymentService employeeContractPaymentService;

	@PostMapping("/employee-contract-payment")
	@IsSuperAdminOrAdmin
	public APIResponse<?> saveEmployeeContractPayment(
			@RequestBody EmployeeContractPaymentDto employeeContractPaymentDto) {
		log.info("[EmployeeContractPaymentController] saveEmployeeContractPayment starts ");
		try {
			EmployeeContractPayment employeeContractPayment = employeeContractPaymentService
					.saveEmployeeContractPayment(employeeContractPaymentDto);
			return Response.getSuccessResponse(employeeContractPayment,
					APIResponseConstants.EMPLOYEE_CONTRACT_PAYMENT_CREATION_RESPONSE, HttpStatus.OK);
		} catch (Exception e) {
			log.error("[EmployeeContractPaymentController] saveEmployeeContractPayment failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-contract-payments")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getEmployeeContractPayments() {
		log.info("[EmployeeContractPaymentController] getEmployeeContractPayments starts ");
		try {
			List<EmployeeContractPayment> contractEmployees = employeeContractPaymentService
					.getEmployeeContractPayments();
			return Response.getSuccessResponse(contractEmployees, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("[EmployeeContractPaymentController] getEmployeeContractPayments failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-contract-payment/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getEmployeeContractPaymentById(@PathVariable("id") Long id) {
		log.info("[EmployeeContractPaymentController] getEmployeeContractPaymentById starts ");
		try {
			EmployeeContractPayment employeeContract = employeeContractPaymentService
					.getEmployeeContractPaymentById(id);
			return Response.getSuccessResponse(employeeContract, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("[EmployeeContractPaymentController] getEmployeeContractPaymentById failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/employee-contract-payment/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateEmployeeContractPayment(@PathVariable Long id,
			@RequestBody EmployeeContractPaymentDto employeeContractPaymentDto) {
		log.info("[EmployeeContractPaymentController] updateEmployeeContractPayment starts ");
		try {
			EmployeeContractPayment employeeContract = employeeContractPaymentService.updateEmployeeContractPayment(id,
					employeeContractPaymentDto);
			return Response.getSuccessResponse(employeeContract,
					APIResponseConstants.EMPLOYEE_CONTRACT_PAYMENT_UPDATE_RESPONSE, HttpStatus.OK);
		} catch (Exception e) {
			log.error("[EmployeeContractPaymentController] updateEmployeeContractPayment failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/employee-contract-payment/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteEmployeeContractPaymentById(@PathVariable("id") Long id) {
		log.info("[EmployeeContractPaymentController] deleteEmployeeContractPaymentById starts ");
		try {
			employeeContractPaymentService.deleteEmployeeContractPaymentById(id);
			return Response.getSuccessResponse(null, APIResponseConstants.DELETE_RESPONSE_MESSAGE,
					HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			log.error("[EmployeeContractPaymentController] deleteEmployeeContractPaymentById failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-contract-payment-list")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getEmployeeContractPaymentList(@ModelAttribute DataFilter dataFilter,@RequestParam("contractStatus") ContractStatus contractStatus) {
		log.info("[EmployeeContractPaymentController] getEmployeeContractPaymentList starts ");
		try {
			Page<EmployeeContractPayment> employeeContractList = employeeContractPaymentService
					.getEmployeeContractPaymentList(dataFilter,contractStatus);
			return Response.getSuccessResponse(employeeContractList, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("[EmployeeContractPaymentController] getEmployeeContractPaymentList failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/employee-contract-payment-history")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getAllEmployeeContractPaymentHistory(@RequestParam Long id) {
		log.info("[EmployeeContractPaymentController] getEmployeeContractPaymentList starts ");
		try {
			List<EmployeeContractPaymentHistory> contractPaymentHistory = employeeContractPaymentService
					.getAllEmployeeContractPaymentHistory(id);
			return Response.getSuccessResponse(contractPaymentHistory, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("[EmployeeContractPaymentController] getEmployeeContractPaymentList failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}