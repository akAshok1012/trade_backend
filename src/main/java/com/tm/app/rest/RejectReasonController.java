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
import com.tm.app.dto.RejectReasonDto;
import com.tm.app.entity.RejectReason;
import com.tm.app.enums.Rejection;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.service.RejectReasonService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RejectReasonController {

	@Autowired
	private RejectReasonService rejectReasonService;

	@PostMapping("/reject-reason")
	@IsSuperAdminOrAdmin
	public APIResponse<?> saveRejectReason(@RequestBody RejectReasonDto rejectReasonDto) {
		try {
			RejectReason rejectReason = rejectReasonService.saveRejectReason(rejectReasonDto);
			return Response.getSuccessResponse(rejectReason, "Reject Reason Saved Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/reject-reasons")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getRejectReason() {
		try {
			List<RejectReason> rejectReason = rejectReasonService.getRejectReason();
			return Response.getSuccessResponse(rejectReason, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/reject-reason/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getRejectReasonById(@PathVariable("id") Long id) {
		try {
			RejectReason rejectReason = rejectReasonService.getRejectReasonById(id);
			return Response.getSuccessResponse(rejectReason, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/reject-reason/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateRejectReason(@PathVariable Long id, @RequestBody RejectReasonDto rejectReasonDto) {
		try {
			RejectReason rejectReason = rejectReasonService.updateRejectReason(id, rejectReasonDto);
			return Response.getSuccessResponse(rejectReason, "Reject Reason Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/reject-reason/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteRejectReasonById(@PathVariable("id") Long id) {
		try {
			rejectReasonService.deleteRejectReasonById(id);
			return Response.getSuccessResponse(null, "Reject Reason Deleted Successfully", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/reject-reasons-list")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getRejectReasonList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<RejectReason> rejectReason = rejectReasonService.getRejectReasonList(dataFilter);
			return Response.getSuccessResponse(rejectReason, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/order-leave-rejection")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getOrderOrLeaveRejection(@RequestParam("rejection") Rejection rejection) {
		try {
			List<RejectReason> rejectReason = rejectReasonService.getOrderOrLeaveRejection(rejection);
			return Response.getSuccessResponse(rejectReason, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}