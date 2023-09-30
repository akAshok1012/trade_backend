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
import com.tm.app.dto.VehicleTypeDto;
import com.tm.app.entity.VehicleType;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.service.VehicleTypeService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor

public class VehicleTypeController {

	@Autowired
	private VehicleTypeService vehicleTypeService;

	@PostMapping("/vehicle-type")
	@IsSuperAdminOrAdmin
	public APIResponse<?> saveVehicleType(@RequestBody VehicleTypeDto vehicleTypeDto) {
		try {
			VehicleType vehicle = vehicleTypeService.saveVehicleType(vehicleTypeDto);
			return Response.getSuccessResponse(vehicle, "VehicleType Saved Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/vehicle-type")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getVehicleType() {
		try {
			List<VehicleType> vehicle = vehicleTypeService.getVehicleType();
			return Response.getSuccessResponse(vehicle, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/vehicle-type/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getVehicleTypeById(@PathVariable("id") Long id) {
		try {
			VehicleType vehicle = vehicleTypeService.getVehicleTypeById(id);
			return Response.getSuccessResponse(vehicle, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/vehicle-type/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateVehicleType(@PathVariable Long id, @RequestBody VehicleTypeDto vehicleTypeDto) {
		try {
			VehicleType vehicle = vehicleTypeService.updateVehicleType(id, vehicleTypeDto);
			return Response.getSuccessResponse(vehicle, "VehicleType Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/vehicle-type/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteVehicleTypeById(@PathVariable("id") Long id) {
		try {
			vehicleTypeService.deleteVehicleTypeById(id);
			return Response.getSuccessResponse(null, "VehicleType Deleted Successfully", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/vehicle-type-list")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getVehicleTypeList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<VehicleType> vehicle = vehicleTypeService.getVehicleTypeList(dataFilter);
			return Response.getSuccessResponse(vehicle, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}