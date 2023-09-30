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
import com.tm.app.dto.LeaveRejectReasonDto;
import com.tm.app.entity.LeaveRejectReason;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.service.LeaveRejectReasonService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LeaveRejectReasonController {
	
	@Autowired
	private LeaveRejectReasonService leaveRejectReasonService;

	@PostMapping("/leave-reject-reason")
	@IsSuperAdminOrAdmin
	public APIResponse<?> saveLeaveRejectReason(@RequestBody LeaveRejectReasonDto leaveRejectReasonDto) {
		try {
			LeaveRejectReason leaveRejectReason = leaveRejectReasonService.saveLeaveRejectReason(leaveRejectReasonDto);
			return Response.getSuccessResponse(leaveRejectReason, "Reject Reason Saved Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/leave-reject-reasons")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getLeaveRejectReason() {
		try {
			List<LeaveRejectReason> leaveRejectReason = leaveRejectReasonService.getLeaveRejectReason();
			return Response.getSuccessResponse(leaveRejectReason, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/leave-reject-reason/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getLeaveRejectReasonById(@PathVariable("id") Long id) {
		try {
			LeaveRejectReason leaveRejectReason = leaveRejectReasonService.getLeaveRejectReasonById(id);
			return Response.getSuccessResponse(leaveRejectReason, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/leave-reject-reason/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateLeaveRejectReason(@PathVariable Long id, @RequestBody LeaveRejectReasonDto leaveRejectReasonDto) {
		try {
			LeaveRejectReason LeaveRejectReason = leaveRejectReasonService.updateLeaveRejectReason(id, leaveRejectReasonDto);
			return Response.getSuccessResponse(LeaveRejectReason, "Leave Reject Reason Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/leave-reject-reason/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteLeaveRejectReasonById(@PathVariable("id") Long id) {
		try {
			leaveRejectReasonService.deleteLeaveRejectReasonById(id);
			return Response.getSuccessResponse(null, "Leave Reject Reason Deleted Successfully", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/leave-reject-reason-listing")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getLeaveRejectReasonListing(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<LeaveRejectReason> leaveRejectReason = leaveRejectReasonService.getLeaveRejectReasonListing(dataFilter);
			return Response.getSuccessResponse(leaveRejectReason, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}