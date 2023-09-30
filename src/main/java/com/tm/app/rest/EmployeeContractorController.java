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
import com.tm.app.dto.EmployeeContractorDto;
import com.tm.app.entity.EmployeeContractor;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.service.EmployeeContractorService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.APIResponseConstants;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeContractorController {

	@Autowired
	private EmployeeContractorService employeeContractorService;

	@PostMapping("/employee-contractor")
	@IsSuperAdminOrAdmin
	public APIResponse<?> saveEmployeeContractor(@RequestBody EmployeeContractorDto employeeContractorDto) {
		try {
			EmployeeContractor contractor = employeeContractorService.saveEmployeeContractor(employeeContractorDto);
			return Response.getSuccessResponse(contractor,
					String.format(APIResponseConstants.CREATION_SUCCESS_MESSAGE, contractor.getFirstName()),
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-contractors")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getEmployeeContractors() {
		try {
			List<EmployeeContractor> contractor = employeeContractorService.getEmployeeContractors();
			return Response.getSuccessResponse(contractor, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-contractor/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getEmployeeContractorById(@PathVariable("id") Long id) {
		try {
			EmployeeContractor contractor = employeeContractorService.getEmployeeContractorById(id);
			return Response.getSuccessResponse(contractor, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/employee-contractor/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateEmployeeContractor(@PathVariable Long id,
			@RequestBody EmployeeContractorDto employeeContractorDto) {
		try {
			EmployeeContractor contractor = employeeContractorService.updateEmployeeContractor(id,
					employeeContractorDto);
			return Response.getSuccessResponse(contractor,
					String.format(APIResponseConstants.UPDATED_SUCCESS_MESSAGE, contractor.getFirstName()),
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/employee-contractor/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteEmployeeContractorById(@PathVariable("id") Long id) {
		try {
			employeeContractorService.deleteEmployeeContractorById(id);
			return Response.getSuccessResponse(null, APIResponseConstants.DELETE_RESPONSE_MESSAGE,
					HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-contractor-list")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getEmployeeContractorList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<EmployeeContractor> contractor = employeeContractorService.getEmployeeContractorList(dataFilter);
			return Response.getSuccessResponse(contractor, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}