package com.tm.app.rest;

import java.sql.Date;
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
import com.tm.app.dto.EmployeeLeaveManagerDto;
import com.tm.app.dto.EmployeeLeaveStatusDto;
import com.tm.app.entity.EmployeeLeaveManager;
import com.tm.app.enums.EmployeeLeaveStatus;
import com.tm.app.enums.LeaveType;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrEmployee;
import com.tm.app.service.EmployeeLeaveManagerService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.APIResponseConstants;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeLeaveManagerController {

	@Autowired
	private EmployeeLeaveManagerService employeeLeaveManagerService;

	@PostMapping("apply-employee-leave")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> applyEmployeeLeave(@RequestBody EmployeeLeaveManagerDto employeeLeaveManagerDto) {
		try {
			EmployeeLeaveManager empLeaveManager = employeeLeaveManagerService
					.applyEmployeeLeave(employeeLeaveManagerDto);
			return Response.getSuccessResponse(empLeaveManager, "Leave request Raised successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-leaves")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getEmployeeLeaves(@ModelAttribute DataFilter dataFilter,
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "fromDate", required = false) Date fromDate,
			@RequestParam(value = "toDate", required = false) Date toDate,
			@RequestParam EmployeeLeaveStatus employeeLeaveStatus) {
		try {
			Page<EmployeeLeaveManager> empLeaveManager = employeeLeaveManagerService.getEmployeeLeaves(dataFilter, id,
					employeeLeaveStatus, fromDate, toDate);
			return Response.getSuccessResponse(empLeaveManager, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-leave/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getEmployeeLeaveById(@PathVariable("id") Long id) {
		try {
			EmployeeLeaveManager empLeaveManager = employeeLeaveManagerService.getEmployeeLeaveById(id);
			return Response.getSuccessResponse(empLeaveManager, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/approve-employee-leave/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> approveEmployeeLeave(@PathVariable Long id,
			@RequestBody EmployeeLeaveManagerDto employeeLeaveManagerDto) {
		try {
			EmployeeLeaveManager empLeaveManager = employeeLeaveManagerService.approveEmployeeLeave(id,
					employeeLeaveManagerDto);
			return Response.getSuccessResponse(empLeaveManager, "Leave Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/employee-leave/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> deleteEmployeeLeaveById(@PathVariable("id") Long id) {
		try {
			employeeLeaveManagerService.deleteEmployeeLeaveById(id);
			return Response.getSuccessResponse(null, APIResponseConstants.DELETE_RESPONSE_MESSAGE,
					HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-leave-status")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getEmployeeLeaveStatus(@RequestParam("LeaveStatus") EmployeeLeaveStatus employeeLeaveStatus) {
		try {
			List<EmployeeLeaveManager> employeeLeaveManager = employeeLeaveManagerService
					.getEmployeeLeaveStatus(employeeLeaveStatus);
			return Response.getSuccessResponse(employeeLeaveManager, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee-leave-type")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getEmployeeLeaveType(@RequestParam("LeaveType") LeaveType leaveType) {
		try {
			List<EmployeeLeaveManager> employeeLeaveManager = employeeLeaveManagerService.getLeaveType(leaveType);
			return Response.getSuccessResponse(employeeLeaveManager, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/leaveBy-employee-id")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getLeaveByEmployeeId(@RequestParam("id") Long id) {
		try {
			List<EmployeeLeaveManager> employeeLeaveManager = employeeLeaveManagerService.getLeaveByEmployeeId(id);
			return Response.getSuccessResponse(employeeLeaveManager, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/leaveBy-employee")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getLeaveByEmployee(
			@RequestParam(value = "status", required = true) EmployeeLeaveStatus employeeLeaveStatus,
			@RequestParam(value = "name", required = false) String name) {
		try {
			List<EmployeeLeaveManager> employeeLeaveManager = employeeLeaveManagerService.getLeaveByEmployee(name,
					employeeLeaveStatus);
			return Response.getSuccessResponse(employeeLeaveManager, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/employee-notification")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getEmployeeLeaveStatus(@RequestParam("id") Long id) {
		try {
			List<EmployeeLeaveStatusDto> employeeLeaveStatusDto = employeeLeaveManagerService.getEmployeeLeaveStatus(id);
			return Response.getSuccessResponse(employeeLeaveStatusDto, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/employee-notification-enabled")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getIsNotificationEnabled(@RequestParam("id") Long id) {
		try {
			EmployeeLeaveManager employeeLeaveManagers = employeeLeaveManagerService.getIsNotificationEnabled(id);
			return Response.getSuccessResponse(employeeLeaveManagers, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}