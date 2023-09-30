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
import com.tm.app.dto.LeadGenerationDto;
import com.tm.app.entity.LeadGeneration;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrEmployee;
import com.tm.app.service.LeadGenerationService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LeadGenerationController {

	@Autowired
	private LeadGenerationService leadGenerationService;

	@PostMapping("/lead-generation")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> saveLeadGeneration(@RequestBody LeadGenerationDto leadGenerationDto) {
		try {
			LeadGeneration leadGeneration = leadGenerationService.saveLeadGeneration(leadGenerationDto);
			return Response.getSuccessResponse(leadGeneration, "Lead Generation Created Successfully", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/lead-generations")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getLeadGeneration() {
		try {
			List<LeadGeneration> leadGeneration = leadGenerationService.getLeadGeneration();
			return Response.getSuccessResponse(leadGeneration, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/lead-generation/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getLeadGenerationById(@PathVariable("id") Long id) {
		try {
			LeadGeneration leadGeneration = leadGenerationService.getLeadGenerationById(id);
			return Response.getSuccessResponse(leadGeneration, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/lead-generation/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> updateLeadGeneration(@PathVariable Long id,
			@RequestBody LeadGenerationDto leadGenerationDto) {
		try {
			LeadGeneration leadGeneration = leadGenerationService.updateLeadGeneration(id, leadGenerationDto);
			return Response.getSuccessResponse(leadGeneration, "Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/lead-generation/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> deleteLeadGenerationById(@PathVariable("id") Long id) {
		try {
			leadGenerationService.deleteLeadGenerationById(id);
			return Response.getSuccessResponse(null, "Lead Generation Deleted Successfully", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/lead-generations-list")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getLeadGenerationList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<LeadGeneration> leadGeneration = leadGenerationService.getLeadGenerationList(dataFilter);
			return Response.getSuccessResponse(leadGeneration, "Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}