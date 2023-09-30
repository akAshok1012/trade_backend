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
import com.tm.app.dto.EmployeeSwipeEntryDto;
import com.tm.app.entity.EmployeeSwipeEntry;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrEmployee;
import com.tm.app.service.EmployeeSwipeEntryService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeSwipeEntryController {

	@Autowired
	private EmployeeSwipeEntryService employeeSwipeEntryService;

	@PostMapping("/employee-swipe-entry")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> saveEmployeeSwipeEntry(@RequestBody EmployeeSwipeEntryDto employeeSwipeEntryDto) {
		try {
			EmployeeSwipeEntry employeeSwipeEntry = employeeSwipeEntryService
					.saveEmployeeSwipeEntry(employeeSwipeEntryDto);
			return Response.getSuccessResponse(employeeSwipeEntry, " Created Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-swipe-entries")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getEmployeeSwipeEntry() {
		try {
			List<EmployeeSwipeEntry> employeeSwipeEntry = employeeSwipeEntryService.getEmployeeSwipeEntry();
			return Response.getSuccessResponse(employeeSwipeEntry, "Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-swipe-entry/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getEmployeeSwipeEntryById(@PathVariable("id") Long id) {
		try {
			EmployeeSwipeEntry employeeSwipeEntry = employeeSwipeEntryService.getEmployeeSwipeEntryById(id);
			return Response.getSuccessResponse(employeeSwipeEntry, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/employee-swipe-entry/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateEmployeeSwipeEntry(@PathVariable Long id,
			@RequestBody EmployeeSwipeEntryDto employeeSwipeEntryDto) {
		try {
			EmployeeSwipeEntry employeeSwipeEntry = employeeSwipeEntryService.updateEmployeeSwipeEntry(id,
					employeeSwipeEntryDto);
			return Response.getSuccessResponse(employeeSwipeEntry, "Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/employee-swipe-entry/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteEmployeeSwipeEntryById(@PathVariable("id") Long id) {
		try {
			employeeSwipeEntryService.deleteEmployeeSwipeEntryById(id);
			return Response.getSuccessResponse(null, "Employee Swipe Entry Deleted Successfully",
					HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-swipe-entries-list")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getEmployeeSwipeEntryList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<EmployeeSwipeEntry> employeeSwipeEntry = employeeSwipeEntryService
					.getEmployeeSwipeEntryList(dataFilter);
			return Response.getSuccessResponse(employeeSwipeEntry, "Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
