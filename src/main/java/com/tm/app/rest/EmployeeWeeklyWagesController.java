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
import com.tm.app.dto.EmployeeWeeklyWagesDto;
import com.tm.app.entity.EmployeeWeeklyWages;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrEmployee;
import com.tm.app.service.EmployeeWeeklyWagesService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeWeeklyWagesController {

	@Autowired
	private EmployeeWeeklyWagesService employeeWeeklyWagesService;
	
	@PostMapping("/employee-weekly-wages")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> saveEmployeeWeeklyWages(@RequestBody EmployeeWeeklyWagesDto employeeWeeklyWagesDto) {
		try {
			EmployeeWeeklyWages employeeWeeklyWages = employeeWeeklyWagesService.saveEmployeeWeeklyWages(employeeWeeklyWagesDto);
			return Response.getSuccessResponse(employeeWeeklyWages, "Saved Successfully", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-weekly-wages")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getEmployeeWeeklyWages() {
		try {
			List<EmployeeWeeklyWages> employeeWeeklyWages = employeeWeeklyWagesService.getEmployeeWeeklyWages();
			return Response.getSuccessResponse(employeeWeeklyWages, "Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-weekly-wages/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getEmployeeWeeklyWagesById(@PathVariable("id") Long id) {
		try {
			EmployeeWeeklyWages employeeWeeklyWages = employeeWeeklyWagesService.getEmployeePayHoursById(id);
			return Response.getSuccessResponse(employeeWeeklyWages, "Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/employee-weekly-wages/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> updateEmployeeWeeklyWages(@PathVariable("id") Long id,
			@RequestBody EmployeeWeeklyWagesDto employeeWeeklyWagesDto) {
		try {
			EmployeeWeeklyWages employeeWeeklyWages = employeeWeeklyWagesService.updateEmployeeWeeklyWages(id, employeeWeeklyWagesDto);
			return Response.getSuccessResponse(employeeWeeklyWages, "Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/employee-weekly-wages/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> deleteEmployeeWeeklyWages(@PathVariable("id") Long id) {
		try {
			employeeWeeklyWagesService.deleteEmployeeWeeklyWages(id);
			return Response.getSuccessResponse(null, "Deleted Successfully", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-weekly-wages-list")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getEmployeeWeeklyWagesList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<EmployeeWeeklyWages> employeeWeeklyWages = employeeWeeklyWagesService.getEmployeeWeeklyWagesList(dataFilter);
			return Response.getSuccessResponse(employeeWeeklyWages, "Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
