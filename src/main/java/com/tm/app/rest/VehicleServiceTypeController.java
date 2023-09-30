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
import com.tm.app.dto.VehicleServiceTypeDto;
import com.tm.app.entity.VehicleServiceType;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.service.VehicleServiceTypeService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor

public class VehicleServiceTypeController {

	@Autowired
	private VehicleServiceTypeService vehicleServiceTypeService;

	@PostMapping("/vehicle-service-type")
	@IsSuperAdminOrAdmin
	public APIResponse<?> saveVehicleServiceType(@RequestBody VehicleServiceTypeDto vehicleServiceTypeDto) {
		try {
			VehicleServiceType vehicle = vehicleServiceTypeService.saveVehicleServiceType(vehicleServiceTypeDto);
			return Response.getSuccessResponse(vehicle, "VehicleServiceType Saved Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/vehicle-service-type")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getVehicleServiceType() {
		try {
			List<VehicleServiceType> vehicle = vehicleServiceTypeService.getVehicleServiceType();
			return Response.getSuccessResponse(vehicle, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/vehicle-service-type/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getVehicleServiceTypeById(@PathVariable("id") Long id) {
		try {
			VehicleServiceType vehicle = vehicleServiceTypeService.getVehicleServiceTypeById(id);
			return Response.getSuccessResponse(vehicle, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/vehicle-service-type/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateVehicleServiceType(@PathVariable Long id,
			@RequestBody VehicleServiceTypeDto vehicleTypeServiceTypeDto) {
		try {
			VehicleServiceType vehicle = vehicleServiceTypeService.updateVehicleServiceType(id,
					vehicleTypeServiceTypeDto);
			return Response.getSuccessResponse(vehicle, "VehicleServiceType Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/vehicle-service-type/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteVehicleServiceTypeById(@PathVariable("id") Long id) {
		try {
			vehicleServiceTypeService.deleteVehicleServiceTypeById(id);
			return Response.getSuccessResponse(null, "VehicleServiceType Deleted Successfully", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/vehicle-service-type-list")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getVehicleServiceTypeList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<VehicleServiceType> vehicle = vehicleServiceTypeService.getVehicleServiceTypeList(dataFilter);
			return Response.getSuccessResponse(vehicle, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}