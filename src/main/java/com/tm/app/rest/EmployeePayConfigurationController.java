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
import com.tm.app.dto.EmployeePayConfigurationDto;
import com.tm.app.entity.EmployeePayConfiguration;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.service.EmployeePayConfigurationService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeePayConfigurationController {

	@Autowired
	private EmployeePayConfigurationService employeePayConfigurationService;

	@PostMapping("/employee-pay-configuration")
	@IsSuperAdminOrAdmin
	public APIResponse<?> saveEmployeePayConfiguration(
			@RequestBody EmployeePayConfigurationDto employeePayConfigurationDto) {
		try {
			EmployeePayConfiguration employeePayConfiguration = employeePayConfigurationService
					.saveEmployeePayConfiguration(employeePayConfigurationDto);
			return Response.getSuccessResponse(employeePayConfiguration,
					"Employee Pay Configuration Saved Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-pay-configurations")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getEmployeePayConfigurations() {
		try {
			List<EmployeePayConfiguration> employeePayConfiguration = employeePayConfigurationService
					.getEmployeePayConfigurations();
			return Response.getSuccessResponse(employeePayConfiguration, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-pay-configuration/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getEmployeePayConfigurationById(@PathVariable("id") Long id) {
		try {
			EmployeePayConfiguration employeePayConfiguration = employeePayConfigurationService
					.getEmployeePayConfigurationById(id);
			return Response.getSuccessResponse(employeePayConfiguration, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/employee-pay-configuration")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getEmployeePayConfiguration() {
		try {
			EmployeePayConfiguration employeePayConfiguration = employeePayConfigurationService
					.getEmployeePayConfiguration();
			return Response.getSuccessResponse(employeePayConfiguration, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/employee-pay-configuration/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateEmployeePayConfiguration(@PathVariable Long id,
			@RequestBody EmployeePayConfigurationDto employeePayConfigurationDto) {
		try {
			EmployeePayConfiguration employeePayConfiguration = employeePayConfigurationService
					.updateEmployeePayConfiguration(id, employeePayConfigurationDto);
			return Response.getSuccessResponse(employeePayConfiguration,
					"Employee Pay Configuration Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/employee-pay-configuration/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteEmployeePayConfigurationById(@PathVariable("id") Long id) {
		try {
			employeePayConfigurationService.deleteEmployeePayConfigurationById(id);
			return Response.getSuccessResponse(null, "Employee Pay Configuration Deleted Successfully",
					HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-pay-configuration-list")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getEmployeePayConfigurationList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<EmployeePayConfiguration> employeePayConfiguration = employeePayConfigurationService
					.getEmployeePayConfigurationList(dataFilter);
			return Response.getSuccessResponse(employeePayConfiguration, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
