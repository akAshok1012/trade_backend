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
import com.tm.app.dto.VehicleDetailsDto;
import com.tm.app.entity.VehicleDetails;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.service.VehicleDetailsService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.APIResponseConstants;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VehicleDetailsController {

	@Autowired
	private VehicleDetailsService vehicleDetailsService;

	@PostMapping("/vehicle-details")
	@IsSuperAdminOrAdmin
	public APIResponse<?> saveVehicleDetails(@RequestBody VehicleDetailsDto vehicleDetailsDto) {
		try {
			VehicleDetails vehicle = vehicleDetailsService.saveVehicleDetails(vehicleDetailsDto);
			return Response.getSuccessResponse(vehicle, String.format(APIResponseConstants.CREATION_SUCCESS_MESSAGE,vehicle.getVehicleMake()), HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/vehicle-details")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getVehicleDetails() {
		try {
			List<VehicleDetails> vehicle = vehicleDetailsService.getVehicleDetails();
			return Response.getSuccessResponse(vehicle, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/vehicle-detail/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getVehicleDetailsById(@PathVariable("id") Long id) {
		try {
			VehicleDetails vehicle = vehicleDetailsService.getVehicleDetailsById(id);
			return Response.getSuccessResponse(vehicle, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/vehicle-detail/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateVehicleDetails(@PathVariable Long id,
			@RequestBody VehicleDetailsDto vehicleDetailsDto) {
		try {
			VehicleDetails vehicle = vehicleDetailsService.updateVehicleDetails(id, vehicleDetailsDto);
			return Response.getSuccessResponse(vehicle, String.format(APIResponseConstants.UPDATED_SUCCESS_MESSAGE,vehicle.getVehicleMake()), HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/vehicle-detail/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteVehicleDetailsById(@PathVariable("id") Long id) {
		try {
			vehicleDetailsService.deleteVehicleDetailsById(id);
			return Response.getSuccessResponse(null, APIResponseConstants.DELETE_RESPONSE_MESSAGE, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/vehicle-details-list")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getVehicleDetailsList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<VehicleDetails> vehicle = vehicleDetailsService.getVehicleDetailsList(dataFilter);
			return Response.getSuccessResponse(vehicle, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}