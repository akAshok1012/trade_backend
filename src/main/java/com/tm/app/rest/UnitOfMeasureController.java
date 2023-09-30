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
import com.tm.app.dto.UnitOfMeasureDto;
import com.tm.app.entity.UnitOfMeasure;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrEmployee;
import com.tm.app.service.UnitOfMeasureService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class UnitOfMeasureController {

	@Autowired
	private UnitOfMeasureService unitOfMeasureService;

	@PostMapping("/unit-of-measure")
	@IsSuperAdminOrAdmin
	public APIResponse<?> saveUnitOfMeasure(@RequestBody UnitOfMeasureDto unitOfMeasureDto) {
		try {
			UnitOfMeasure unitOfMeasure = unitOfMeasureService.saveUnitOfMeasure(unitOfMeasureDto);
			return Response.getSuccessResponse(unitOfMeasure,"'"+unitOfMeasure.getUnitName()+"'"+" Created Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/unit-of-measures")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getUnitOfMeasures() {
		try {
			List<UnitOfMeasure> unitOfMeasures = unitOfMeasureService.getUnitOfMeasures();
			return Response.getSuccessResponse(unitOfMeasures, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/unit-of-measure/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getUnitOfMeasureById(@PathVariable("id") Long id) {
		try {
			UnitOfMeasure unitOfMeasure = unitOfMeasureService.getUnitOfMeasureById(id);
			return Response.getSuccessResponse(unitOfMeasure, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/unit-of-measure/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateUnitOfMeasure(@PathVariable Long id, @RequestBody UnitOfMeasureDto unitOfMeasureDto) {
		try {
			UnitOfMeasure unitOfMeasure = unitOfMeasureService.updateUnitOfMeasure(id, unitOfMeasureDto);
			return Response.getSuccessResponse(unitOfMeasure,"'"+unitOfMeasure.getUnitName()+"'"+" Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/unit-of-measure/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteUnitOfMeasureById(@PathVariable("id") Long id) {
		try {
			unitOfMeasureService.deleteUnitOfMeasureById(id);
			return Response.getSuccessResponse(null, "Unit Of Measure Deleted Successfully", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/unit-of-measure-list")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getUnitOfMeasureList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<UnitOfMeasure> unitOfMeasures = unitOfMeasureService.getUnitOfMeasureList(dataFilter);
			return Response.getSuccessResponse(unitOfMeasures, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}