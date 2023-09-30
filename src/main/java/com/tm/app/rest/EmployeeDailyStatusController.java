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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.EmployeeDailyStatusDto;
import com.tm.app.entity.EmployeeDailyStatus;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrEmployee;
import com.tm.app.service.EmployeeDailyStatusService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeDailyStatusController {

	@Autowired
	private EmployeeDailyStatusService employeeDailyStatusService;

	@PostMapping("/employee-daily-status")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> saveEmployeeDailyStatus(@RequestBody EmployeeDailyStatusDto employeeDailyStatusDto) {
		try {
			EmployeeDailyStatus empDailyStatus = employeeDailyStatusService
					.saveEmployeeDailyStatus(employeeDailyStatusDto);
			return Response.getSuccessResponse(empDailyStatus, "Daily Status Created Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-daily-status")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getEmployeeDailyStatus() {
		try {
			List<EmployeeDailyStatus> empDailyStatus = employeeDailyStatusService.getEmployeeDailyStatus();
			return Response.getSuccessResponse(empDailyStatus, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-daily-status/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getEmployeeDailyStatusById(@PathVariable("id") Long id) {
		try {
			EmployeeDailyStatus empDailyStatus = employeeDailyStatusService.getEmployeeDailyStatusById(id);
			return Response.getSuccessResponse(empDailyStatus, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/employee-daily-status/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> updateEmployeeDailyStatus(@PathVariable Long id,
			@RequestBody EmployeeDailyStatusDto employeeDailyStatusDto) {
		try {
			EmployeeDailyStatus empDailyStatus = employeeDailyStatusService.updateEmployeeDailyStatus(id,
					employeeDailyStatusDto);
			return Response.getSuccessResponse(empDailyStatus, " DailyStatus Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/employee-daily-status/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteEmployeeDailyStatusById(@PathVariable("id") Long id) {
		try {
			employeeDailyStatusService.deleteEmployeeDailyStatusById(id);
			return Response.getSuccessResponse(null, "Employee DailyStatus Deleted Successfully",
					HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-daily-status-view")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getEmployeeDailyStatusByEmployee(@ModelAttribute DataFilter dataFilter,
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate) {
		try {
			Page<EmployeeDailyStatus> empDailyStatus = employeeDailyStatusService
					.getEmployeeDailyStatusByEmployee(dataFilter, id, fromDate, toDate);
			return Response.getSuccessResponse(empDailyStatus, "EmployeeDailyStatus Datas", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}