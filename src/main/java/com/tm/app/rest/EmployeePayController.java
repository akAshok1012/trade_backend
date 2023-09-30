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
import com.tm.app.dto.EmployeePayDto;
import com.tm.app.entity.EmployeePay;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.service.EmployeePayService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class EmployeePayController {

	@Autowired
	private EmployeePayService employeePayService;

	@PostMapping("/employee-pay")
	@IsSuperAdminOrAdmin
	public APIResponse<?> saveEmployeePay(@RequestBody EmployeePayDto employeePayDto) {
		try {
			EmployeePay employeePay = employeePayService.saveEmployeePay(employeePayDto);
			return Response.getSuccessResponse(employeePay, "Employee Pay Saved Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-pays")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getEmployeePays() {
		try {
			List<EmployeePay> employeePay = employeePayService.getEmployeePays();
			return Response.getSuccessResponse(employeePay, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-pay/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getEmployeePayById(@PathVariable("id") Long id) {
		try {
			EmployeePay employeePay = employeePayService.getEmployeePayById(id);
			return Response.getSuccessResponse(employeePay, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/employee-pay/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateEmployeePay(@PathVariable Long id, @RequestBody EmployeePayDto employeePayDto) {
		try {
			EmployeePay employeePay = employeePayService.updateEmployeePay(id, employeePayDto);
			return Response.getSuccessResponse(employeePay, "Employee Pay Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/employee-pay/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteEmployeePayById(@PathVariable("id") Long id) {
		try {
			employeePayService.deleteEmployeePayById(id);
			return Response.getSuccessResponse(null, "EmployeePay Deleted Successfully", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/employee-pay-report/{empId}")	
	@IsSuperAdminOrAdmin
	public APIResponse<?> getEmployeePayByEmployee(@PathVariable("empId") Long empId) {
		try {
			List<EmployeePay> employeePay = employeePayService.getEmployeePayByEmployee(empId);
			return Response.getSuccessResponse(employeePay, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/employee-pay-list")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getEmployeePayList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<EmployeePay> employeePay = employeePayService.getEmployeePayList(dataFilter);
			return Response.getSuccessResponse(employeePay, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}