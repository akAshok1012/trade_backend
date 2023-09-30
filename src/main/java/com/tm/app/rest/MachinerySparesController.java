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
import com.tm.app.dto.MachinerySparesDto;
import com.tm.app.entity.MachinerySpares;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrEmployee;
import com.tm.app.service.MachinerySparesService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin
public class MachinerySparesController {

	@Autowired
	private MachinerySparesService machinerySparesService;
	
	@PostMapping("/machinery-spares")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> saveMachinerySpares(@RequestBody MachinerySparesDto machinerySparesDto) {
		try {
			MachinerySpares machinerySpares = machinerySparesService.saveMachinerySpares(machinerySparesDto);
			return Response.getSuccessResponse(machinerySpares, " created successfully", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/machinery-spares")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getMachinerySpares() {
		try {
			List<MachinerySpares> machinerySpares = machinerySparesService.getMachinerySpares();
			return Response.getSuccessResponse(machinerySpares, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/machinery-spares/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getMachinerySparesById(@PathVariable("id") Long id) {
		try {
			MachinerySpares machinerySpares = machinerySparesService.getMachinerySparesById(id);
			return Response.getSuccessResponse(machinerySpares, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/machinery-spares/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> updateMachinerySpares(@PathVariable Long id, @RequestBody MachinerySparesDto machinerySparesDto) {
		try {
			MachinerySpares machinerySpares = machinerySparesService.updateMachinerySpares(id, machinerySparesDto);
			return Response.getSuccessResponse(machinerySpares, " Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/machinery-spares/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> deleteMachinerySparesById(@PathVariable("id") Long id) {
		try {
			machinerySparesService.deleteMachinerySparesById(id);
			return Response.getSuccessResponse(null, "Machinery Spares Deleted Successfully", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/machinery-spares-list")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getMachinerySparesList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<MachinerySpares> machinerySpares = machinerySparesService.getMachinerySparesList(dataFilter);
			return Response.getSuccessResponse(machinerySpares, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
