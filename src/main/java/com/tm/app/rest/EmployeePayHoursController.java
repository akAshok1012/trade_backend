package com.tm.app.rest;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.tm.app.dto.EmployeePay;
import com.tm.app.dto.EmployeePayHoursDto;
import com.tm.app.entity.EmployeePayHours;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrEmployee;
import com.tm.app.service.EmployeePayHoursService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeePayHoursController {

	@Autowired
	private EmployeePayHoursService employeePayHoursService;

	@PostMapping("/employee-pay-hour")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> saveEmployeePayHours(@RequestBody EmployeePayHoursDto employeePayHoursDto) {
		try {
			EmployeePayHours employeePayHours = employeePayHoursService.saveEmployeePayHours(employeePayHoursDto);
			return Response.getSuccessResponse(employeePayHours, "Saved Successfully", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-pay-hours")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getEmployeePayHours() {
		try {
			List<EmployeePayHours> employeePayHours = employeePayHoursService.getEmployeePayHours();
			return Response.getSuccessResponse(employeePayHours, "Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-pay-hours/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getEmployeePayHoursById(@PathVariable("id") Long id) {
		try {
			EmployeePayHours employeePayHours = employeePayHoursService.getEmployeePayHoursById(id);
			return Response.getSuccessResponse(employeePayHours, "Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/employee-pay-hours/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> updateEmployeePayHours(@PathVariable("id") Long id,
			@RequestBody EmployeePayHoursDto employeePayHoursDto) {
		try {
			EmployeePayHours employeePayHours = employeePayHoursService.updateEmployeePayHours(id, employeePayHoursDto);
			return Response.getSuccessResponse(employeePayHours, "Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/employee-pay-hours/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> deleteEmployeePayHours(@PathVariable("id") Long id) {
		try {
			employeePayHoursService.deleteEmployeePayHours(id);
			return Response.getSuccessResponse(null, "Deleted Successfully", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-pay-hours-list")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getEmployeePayHoursList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<EmployeePayHours> employeePayHours = employeePayHoursService.getEmployeePayHoursList(dataFilter);
			return Response.getSuccessResponse(employeePayHours, "Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-pay-hours-filter")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getEmployeePayHoursFilter(@RequestParam("id") Long id,
			@RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
			@RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
		try {
			EmployeePay employeePay = employeePayHoursService.getEmployeePayHoursFilter(id, fromDate, toDate);
			return Response.getSuccessResponse(employeePay, "Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
