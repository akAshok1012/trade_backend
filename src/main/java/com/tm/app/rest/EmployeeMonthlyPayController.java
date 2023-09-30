package com.tm.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import com.tm.app.dto.DataFilter;
import com.tm.app.entity.EmployeeMonthlyPay;
import com.tm.app.security.annotations.IsSuperAdmin;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.service.EmployeeMonthlyPayService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.APIResponseConstants;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeMonthlyPayController {

	@Autowired
	private EmployeeMonthlyPayService employeeMonthlyPayService;

	@GetMapping("/employee-monthly-pay")
	@IsSuperAdmin
	public APIResponse<?> getEmployeeMonthlyPay() {
		try {
			List<EmployeeMonthlyPay> employeeMonthlyPay = employeeMonthlyPayService.getEmployeeMonthlyPay();
			return Response.getSuccessResponse(employeeMonthlyPay, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/employee-monthly-pay-list")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getEmployeeMonthlyPayList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<EmployeeMonthlyPay> employeeMonthlyPay = employeeMonthlyPayService.getEmployeeMonthlyPayList(dataFilter);
			return Response.getSuccessResponse(employeeMonthlyPay, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
