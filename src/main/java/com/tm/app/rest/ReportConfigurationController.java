package com.tm.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tm.app.dto.ReportConfigurationDto;
import com.tm.app.entity.ReportConfiguration;
import com.tm.app.service.ReportConfigurationService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.APIResponseConstants;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class ReportConfigurationController {

	@Autowired
	private ReportConfigurationService reportConfigurationService;

	@PostMapping("/add-report-configuration")
	public APIResponse<?> addReportConfiguration(@RequestBody ReportConfigurationDto configurationDto) {
		try {
			ReportConfiguration reportConfiguration = reportConfigurationService
					.addReportConfiguration(configurationDto);
			return Response.getSuccessResponse(reportConfiguration, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/report-configurations")
	public APIResponse<?> getAllReportConfiguration() {
		try {
			List<ReportConfiguration> reportConfiguration = reportConfigurationService.getAllReportConfiguration();
			return Response.getSuccessResponse(reportConfiguration, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/report-configuration")
	public APIResponse<?> getReportConfigurationByTemplateName(@RequestParam("templateName") String templateName) {
		try {
			ReportConfiguration reportConfiguration = reportConfigurationService
					.getReportConfigurationByTemplateName(templateName);
			return Response.getSuccessResponse(reportConfiguration, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/report-configuration")
	public APIResponse<?> deleteReportConfigurationByTemplateName(@RequestParam("id") Long id) {
		try {
			reportConfigurationService.deleteReportConfigurationById(id);
			return Response.getSuccessResponse(null, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}