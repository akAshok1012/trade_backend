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
import com.tm.app.dto.MachineryDto;
import com.tm.app.entity.Machinery;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrEmployee;
import com.tm.app.service.MachineryService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class MachineryController {

	@Autowired
	private MachineryService machineryService;

	@PostMapping("/machinery")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> saveMachinery(@RequestBody MachineryDto machineryDto,HttpServletRequest request) {
		try {
			Machinery machinery = machineryService.saveMachinery(machineryDto,request);
			return Response.getSuccessResponse(machinery,"'"+machinery.getName() +"'"+ " created successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/machineries")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getMachineries() {
		try {
			List<Machinery> machinery = machineryService.getMachineries();
			return Response.getSuccessResponse(machinery, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/machinery/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getMachineryById(@PathVariable("id") Long id) {
		try {
			Machinery machinery = machineryService.getMachineryById(id);
			return Response.getSuccessResponse(machinery, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/machinery/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> updateMachinery(@PathVariable Long id, @RequestBody MachineryDto machineryDto,HttpServletRequest request) {
		try {
			Machinery machinery = machineryService.updateMachinery(id, machineryDto,request);
			return Response.getSuccessResponse(machinery,machinery.getName()+ " Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/machinery/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> deleteMachineryById(@PathVariable("id") Long id) {
		try {
			machineryService.deleteMachineryById(id);
			return Response.getSuccessResponse(null, "Machinery Deleted Successfully", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/machinery-list")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getMachineryList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<Machinery> machinery = machineryService.getMachineryList(dataFilter);
			return Response.getSuccessResponse(machinery, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}