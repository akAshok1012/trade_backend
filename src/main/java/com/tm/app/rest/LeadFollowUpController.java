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
import com.tm.app.dto.LeadFollowUpDto;
import com.tm.app.entity.LeadFollowUp;
import com.tm.app.enums.LeadStatus;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrEmployee;
import com.tm.app.service.LeadFollowUpService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LeadFollowUpController {

	@Autowired
	private LeadFollowUpService leadFollowUpService;

	@PostMapping("/lead-follow-up")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> saveLeadFollowUp(@RequestBody LeadFollowUpDto leadFollowUpDto) {
		try {
			LeadFollowUp leadFollowUp = leadFollowUpService.saveLeadFollowUp(leadFollowUpDto);
			return Response.getSuccessResponse(leadFollowUp, "Lead FollowUp Created Successfully", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/lead-follow-up")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getLeadFollowUp() {
		try {
			List<LeadFollowUp> leadFollowUp = leadFollowUpService.getLeadFollowUp();
			return Response.getSuccessResponse(leadFollowUp, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/lead-follow-up/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getLeadFollowUpById(@PathVariable("id") Long id) {
		try {
			LeadFollowUp leadFollowUp = leadFollowUpService.getLeadFollowUpById(id);
			return Response.getSuccessResponse(leadFollowUp, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/lead-follow-up/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> updateLeadFollowUp(@PathVariable Long id, @RequestBody LeadFollowUpDto leadFollowUpDto) {
		try {
			LeadFollowUp leadFollowUp = leadFollowUpService.updateLeadFollowUp(id, leadFollowUpDto);
			return Response.getSuccessResponse(leadFollowUp, "Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/lead-follow-up/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> deleteLeadFollowUpById(@PathVariable("id") Long id) {
		try {
			leadFollowUpService.deleteLeadFollowUpById(id);
			return Response.getSuccessResponse(null, "Lead FollowUp Deleted Successfully", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/lead-follow-up-list")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getLeadFollowUpList(@ModelAttribute DataFilter dataFilter,
			@RequestParam(value = "leadStatus", required = false) LeadStatus leadStatus) {
		try {
			Page<LeadFollowUp> leadFollowUp = leadFollowUpService.getLeadFollowUpList(leadStatus, dataFilter);
			return Response.getSuccessResponse(leadFollowUp, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}