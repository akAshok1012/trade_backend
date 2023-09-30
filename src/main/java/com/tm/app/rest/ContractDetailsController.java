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

import com.tm.app.dto.ContractDetailsDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.entity.ContractDetails;
import com.tm.app.enums.ContractStatus;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.service.ContractDetailsService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.APIResponseConstants;
import com.tm.app.utils.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ContractDetailsController {

	@Autowired
	private ContractDetailsService employeeContractService;

	@PostMapping("/employee-contract")
	@IsSuperAdminOrAdmin
	public APIResponse<?> saveEmployeeContract(@RequestBody ContractDetailsDto employeeContratDto) {
		log.info("[EmployeeContractController] saveEmployeeContract starts ");
		try {
			ContractDetails employeeContract = employeeContractService.saveEmployeeContract(employeeContratDto);
			return Response.getSuccessResponse(employeeContract,
					String.format(APIResponseConstants.CREATION_SUCCESS_MESSAGE, employeeContract.getContractName()),
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("[EmployeeContractController] saveEmployeeContract failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-contracts")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getEmployeeContracts() {
		log.info("[EmployeeContractController] getEmployeeContracts starts ");
		try {
			List<ContractDetails> contractEmployees = employeeContractService.getEmployeeContracts();
			return Response.getSuccessResponse(contractEmployees, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("[EmployeeContractController] getEmployeeContracts failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-contract/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getEmployeeContractById(@PathVariable("id") Long id) {
		log.info("[EmployeeContractController] getEmployeeContractById starts ");
		try {
			ContractDetails employeeContract = employeeContractService.getEmployeeContractById(id);
			return Response.getSuccessResponse(employeeContract, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("[EmployeeContractController] getEmployeeContractById failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/employee-contract/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateEmployeeContract(@PathVariable Long id,
			@RequestBody ContractDetailsDto employeeContratDto) {
		log.info("[EmployeeContractController] updateEmployeeContract starts ");
		try {
			ContractDetails employeeContract = employeeContractService.updateEmployeeContract(id, employeeContratDto);
			return Response.getSuccessResponse(employeeContract,
					String.format(APIResponseConstants.UPDATED_SUCCESS_MESSAGE, employeeContract.getContractName()),
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("[EmployeeContractController] updateEmployeeContract failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/employee-contract/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteEmployeeContractById(@PathVariable("id") Long id) {
		log.info("[EmployeeContractController] deleteEmployeeContractById starts ");
		try {
			employeeContractService.deleteEmployeeContractById(id);
		} catch (Exception e) {
			log.error("[EmployeeContractController] deleteEmployeeContractById failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return Response.getSuccessResponse(null, APIResponseConstants.DELETE_RESPONSE_MESSAGE, HttpStatus.NO_CONTENT);
	}

	@GetMapping("/employee-contract-list")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getEmployeeContractList(@ModelAttribute DataFilter dataFilter,
			@RequestParam("contractStatus") ContractStatus contractStatus) {
		log.info("[EmployeeContractController] getEmployeeContractList starts ");
		try {
			Page<ContractDetails> employeeContractList = employeeContractService.getEmployeeContractList(dataFilter,
					contractStatus);
			return Response.getSuccessResponse(employeeContractList, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("[EmployeeContractController] getEmployeeContractList failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}