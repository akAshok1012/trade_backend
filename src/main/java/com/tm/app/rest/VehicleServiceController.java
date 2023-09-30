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
import com.tm.app.dto.VehicleServiceDto;
import com.tm.app.entity.VehicleService;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.service.VehicleServices;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.APIResponseConstants;
import com.tm.app.utils.Response;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class VehicleServiceController {
	
	@Autowired
	private VehicleServices vehicleServices;

	@PostMapping("/vehicleService-details")
	@IsSuperAdminOrAdmin
	public APIResponse<?> saveVehicleService(@RequestBody VehicleServiceDto vehicleServiceDetailsDto) {
		try {
			VehicleService vehicleService = vehicleServices.saveVehicleService(vehicleServiceDetailsDto);
			return Response.getSuccessResponse(vehicleService, "VehicleService Saved Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/vehicleService-details")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getVehicleServices() {
		try {
			List<VehicleService> vehicleService = vehicleServices.getVehicleServices();
			return Response.getSuccessResponse(vehicleService, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/vehicleService-detail/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getVehicleServiceById(@PathVariable("id") Long id) {
		try {
			VehicleService vehicleService = vehicleServices.getVehicleServiceById(id);
			return Response.getSuccessResponse(vehicleService, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/vehicleService-detail/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateVehicleService(@PathVariable Long id,
			@RequestBody VehicleServiceDto vehicleServiceDetailsDto) {
		try {
			VehicleService vehicleService = vehicleServices.updateVehicleService(id, vehicleServiceDetailsDto);
			return Response.getSuccessResponse(vehicleService, "VehicleService Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/vehicleService-detail/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteVehicleServiceById(@PathVariable("id") Long id) {
		try {
			vehicleServices.deleteVehicleServiceById(id);
			return Response.getSuccessResponse(null, APIResponseConstants.DELETE_RESPONSE_MESSAGE, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/vehicleService-details-list")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getVehicleServicesList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<VehicleService> vehicleService = vehicleServices.getVehicleServicesList(dataFilter);
			return Response.getSuccessResponse(vehicleService, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}