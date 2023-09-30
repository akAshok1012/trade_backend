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
import com.tm.app.dto.EmployeeTypeDto;
import com.tm.app.entity.EmployeeType;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.service.EmployeeTypeService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeTypeController {

	@Autowired
	private EmployeeTypeService employeeTypeService;

	@PostMapping("/employee-type")
	@IsSuperAdminOrAdmin
	public APIResponse<?> saveEmployeeType(@RequestBody EmployeeTypeDto employeeTypeDto) {
		try {
			EmployeeType employeeTypes = employeeTypeService.saveEmployeeType(employeeTypeDto);
			return Response.getSuccessResponse(employeeTypes, "Employee Type Saved Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-types")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getEmployeeTypes() {
		try {
			List<EmployeeType> employeeTypes = employeeTypeService.getEmployeeTypes();
			return Response.getSuccessResponse(employeeTypes, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-type/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getEmployeeTypeById(@PathVariable("id") Long id) {
		try {
			EmployeeType employeeTypes = employeeTypeService.getEmployeeTypeById(id);
			return Response.getSuccessResponse(employeeTypes, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/employee-type/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateEmployeeType(@PathVariable Long id, @RequestBody EmployeeTypeDto employeeTypeDto) {
		try {
			EmployeeType employeeTypes = employeeTypeService.updateEmployeeType(id, employeeTypeDto);
			return Response.getSuccessResponse(employeeTypes, "Employee Type Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/employee-type/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteEmployeeTypeById(@PathVariable("id") Long id) {
		try {
			employeeTypeService.deleteEmployeeTypeById(id);
			return Response.getSuccessResponse(null, "EmployeeType Deleted Successfully", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-type-list")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getEmployeeTypeList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<EmployeeType> employeeTypes = employeeTypeService.getEmployeeTypeList(dataFilter);
			return Response.getSuccessResponse(employeeTypes, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}