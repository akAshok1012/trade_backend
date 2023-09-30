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
import com.tm.app.dto.MaintenanceDto;
import com.tm.app.entity.Maintenance;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrEmployee;
import com.tm.app.service.MaintenanceService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MaintenanceController {

	@Autowired
	private MaintenanceService maintenanceService;

	@PostMapping("/maintenance")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> saveMaintenance(@RequestBody MaintenanceDto maintenanceDto, HttpServletRequest request) {
		try {
			Maintenance maintenance = maintenanceService.saveMaintenance(maintenanceDto, request);
			return Response.getSuccessResponse(maintenance, "Maintenance Created", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/maintenances")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getMaintenances() {
		try {
			List<Maintenance> maintenances = maintenanceService.getMaintenances();
			return Response.getSuccessResponse(maintenances, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/maintenance/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getMaintenanceById(@PathVariable("id") Long id) {
		try {
			Maintenance maintenance = maintenanceService.getMaintenanceById(id);
			return Response.getSuccessResponse(maintenance, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/maintenance/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> updateMaintenance(@PathVariable Long id, @RequestBody MaintenanceDto maintenanceDto,
			HttpServletRequest request) {
		try {
			Maintenance maintenance = maintenanceService.updateMaintenance(id, maintenanceDto, request);
			return Response.getSuccessResponse(maintenance, "Maintenance Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/maintenance/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> deleteMaintenanceById(@PathVariable("id") Long id) {
		try {
			maintenanceService.deleteMaintenanceById(id);
			return Response.getSuccessResponse(null, "Success", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/maintenance-list")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getMaintenanceList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<Maintenance> maintenances = maintenanceService.getMaintenanceList(dataFilter);
			return Response.getSuccessResponse(maintenances, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}