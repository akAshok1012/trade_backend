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
import com.tm.app.dto.EmployeeDepartmentDto;
import com.tm.app.dto.EmployeeDepartmentIdNameDto;
import com.tm.app.entity.EmployeeDepartment;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.service.EmployeeDepartmentService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.APIResponseConstants;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeDepartmentController {

	@Autowired
	private EmployeeDepartmentService employeeDepartmentService;

	@PostMapping("/employee-department")
	@IsSuperAdminOrAdmin
	public APIResponse<?> saveEmployeeDepartment(@RequestBody EmployeeDepartmentDto employeeDepartmentDto) {
		try {
			EmployeeDepartment empDepartments = employeeDepartmentService.saveEmployeeDepartment(employeeDepartmentDto);
			return Response.getSuccessResponse(empDepartments,
					String.format(APIResponseConstants.CREATION_SUCCESS_MESSAGE, empDepartments.getDepartmentName()),
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-departments")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getEmployeeDepartment() {
		try {
			List<EmployeeDepartment> empDepartment = employeeDepartmentService.getEmployeeDepartment();
			return Response.getSuccessResponse(empDepartment, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-department/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getEmployeeDepartmentById(@PathVariable("id") Long id) {
		try {
			EmployeeDepartment empDepartment = employeeDepartmentService.getEmployeeDepartmentById(id);
			return Response.getSuccessResponse(empDepartment, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/employee-department/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateEmployeeDepartment(@PathVariable Long id,
			@RequestBody EmployeeDepartmentDto employeeDepartmentDto) {
		try {
			EmployeeDepartment empDepartments = employeeDepartmentService.updateEmployeeDepartment(id,
					employeeDepartmentDto);
			return Response.getSuccessResponse(empDepartments,
					String.format(APIResponseConstants.UPDATED_SUCCESS_MESSAGE, empDepartments.getDepartmentName()),
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/employee-department/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteEmployeeDepartmentById(@PathVariable("id") Long id) {
		try {
			employeeDepartmentService.deleteDepartmentById(id);
			return Response.getSuccessResponse(null, APIResponseConstants.DELETE_RESPONSE_MESSAGE,
					HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/departent-name")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getEmployeeDepartmentName() {
		try {
			List<EmployeeDepartmentIdNameDto> employeeDepartmentDto = employeeDepartmentService
					.getEmployeeDepartmentName();
			return Response.getSuccessResponse(employeeDepartmentDto, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-department-list")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getEmployeeDepartmentList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<EmployeeDepartment> empDepartment = employeeDepartmentService.getEmployeeDepartmentList(dataFilter);
			return Response.getSuccessResponse(empDepartment, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}