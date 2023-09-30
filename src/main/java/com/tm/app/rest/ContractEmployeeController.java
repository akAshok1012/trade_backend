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

import com.tm.app.dto.ContractEmployeeDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.entity.ContractEmployee;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.service.ContractEmployeeService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.APIResponseConstants;
import com.tm.app.utils.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ContractEmployeeController {

	@Autowired
	private ContractEmployeeService contractEmployeeService;

	@PostMapping("/contract-employee")
	@IsSuperAdminOrAdmin
	public APIResponse<?> saveContractEmployee(@RequestBody ContractEmployeeDto contractEmployeeDto) {
		log.info("[ContractEmployeeController] saveContractEmployee starts ");
		try {
			ContractEmployee contractEmployee = contractEmployeeService.saveContractEmployee(contractEmployeeDto);
			return Response.getSuccessResponse(contractEmployee,
					String.format(APIResponseConstants.CREATION_SUCCESS_MESSAGE, contractEmployee.getName()),
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("[ContractEmployeeController] saveContractEmployee failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/contract-employees")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getContractEmployees() {
		log.info("[ContractEmployeeController] getContractEmployees starts ");
		try {
			List<ContractEmployee> contractEmployees = contractEmployeeService.getContractEmployees();
			return Response.getSuccessResponse(contractEmployees, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("[ContractEmployeeController] getContractEmployees failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/contract-employee/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getContractEmployeeById(@PathVariable("id") Long id) {
		log.info("[ContractEmployeeController] getContractEmployeeById starts ");
		try {
			ContractEmployee contractEmployee = contractEmployeeService.getContractEmployeeById(id);
			return Response.getSuccessResponse(contractEmployee, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("[ContractEmployeeController] getContractEmployeeById failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/contract-employee/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateContractEmployee(@PathVariable Long id,
			@RequestBody ContractEmployeeDto contractEmployeeDto) {
		log.info("[ContractEmployeeController] updateContractor starts ");
		try {
			ContractEmployee contractEmployee = contractEmployeeService.updateContractEmployee(id, contractEmployeeDto);
			return Response.getSuccessResponse(contractEmployee,
					String.format(APIResponseConstants.UPDATED_SUCCESS_MESSAGE, contractEmployee.getName()),
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("[ContractEmployeeController] updateContractEmployee failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/contract-employee/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteContractEmployeeById(@PathVariable("id") Long id) {

		try {
			contractEmployeeService.deleteContractEmployeeById(id);
			return Response.getSuccessResponse(null, APIResponseConstants.DELETE_RESPONSE_MESSAGE,
					HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/contract-employee-list")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getContractEmployeeList(@ModelAttribute DataFilter dataFilter) {
		log.info("[ContractEmployeeController] getContractEmployeeList starts ");
		try {
			Page<ContractEmployee> contractEmployeeList = contractEmployeeService.getContractEmployeeList(dataFilter);
			return Response.getSuccessResponse(contractEmployeeList, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("[ContractEmployeeController] getContractEmployeeList failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/contract-employees-by-contract")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getContractEmployeesNotInContract() {
		log.info("[ContractEmployeeController] getContractEmployees starts ");
		try {
			List<ContractEmployee> contractEmployees = contractEmployeeService.getContractEmployeesNotInContract();
			return Response.getSuccessResponse(contractEmployees, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("[ContractEmployeeController] getContractEmployees failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/contract-employees-by-contractor")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getContractEmployeesByContractor(@RequestParam Long id) {
		log.info("[ContractEmployeeController] getContractEmployees starts ");
		try {
			List<ContractEmployee> contractEmployees = contractEmployeeService.getContractEmployeesByContractor(id);
			return Response.getSuccessResponse(contractEmployees, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("[ContractEmployeeController] getContractEmployees failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}