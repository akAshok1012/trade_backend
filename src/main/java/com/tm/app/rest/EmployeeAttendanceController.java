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
import com.tm.app.dto.EmployeeAttendanceDto;
import com.tm.app.entity.EmployeeAttendance;
import com.tm.app.security.annotations.IsSuperAdmin;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrEmployee;
import com.tm.app.service.EmployeeAttendanceService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.APIResponseConstants;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeAttendanceController {

	@Autowired
	private EmployeeAttendanceService employeeAttendanceService;

	@PostMapping("/employee-attendance")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> saveEmployeeAttendance(@RequestBody EmployeeAttendanceDto employeeAttendanceDto) {
		try {
			EmployeeAttendance employeeAttendance = employeeAttendanceService
					.saveEmployeeAttendance(employeeAttendanceDto);
			return Response.getSuccessResponse(employeeAttendance, APIResponseConstants.CREATION_SUCCESS_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-attendance")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getEmployeeAttendance() {
		try {
			List<EmployeeAttendance> employeeAttendance = employeeAttendanceService.getEmployeeAttendance();
			return Response.getSuccessResponse(employeeAttendance, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-attendance/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getEmployeeAttendanceById(@PathVariable("id") Long id) {
		try {
			EmployeeAttendance employeeAttendance = employeeAttendanceService.getEmployeeAttendanceById(id);
			return Response.getSuccessResponse(employeeAttendance, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/employee-attendance/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> updateEmployeeAttendance(@PathVariable Long id,
			@RequestBody EmployeeAttendanceDto employeeAttendanceDto) {
		try {
			EmployeeAttendance employeeAttendance = employeeAttendanceService.updateEmployeeAttendance(id,
					employeeAttendanceDto);
			return Response.getSuccessResponse(employeeAttendance, APIResponseConstants.UPDATED_SUCCESS_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/employee-attendance/{id}")
	@IsSuperAdmin
	public APIResponse<?> deleteEmployeeAttendanceById(@PathVariable("id") Long id) {
		try {
			employeeAttendanceService.deleteEmployeeAttendanceById(id);
			return Response.getSuccessResponse(null, APIResponseConstants.DELETE_RESPONSE_MESSAGE,
					HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-attendance-list")
	public APIResponse<?> getEmployeeAttendanceList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<EmployeeAttendance> employeeAttendance = employeeAttendanceService
					.getEmployeeAttendanceList(dataFilter);
			return Response.getSuccessResponse(employeeAttendance, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}