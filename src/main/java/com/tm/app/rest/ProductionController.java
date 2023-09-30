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
import com.tm.app.dto.ProductionDto;
import com.tm.app.entity.Production;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrEmployee;
import com.tm.app.service.ProductionService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductionController {

	@Autowired
	private ProductionService productionService;

	@PostMapping("/production")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> saveProduction(@RequestBody ProductionDto productionDto, HttpServletRequest request) {
		try {
			Production production = productionService.saveProduction(productionDto, request);
			return Response.getSuccessResponse(production, "Production Saved Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/productions")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getAllProduction() {
		try {
			List<Production> productions = productionService.getAllProduction();
			return Response.getSuccessResponse(productions, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/production/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getProductionById(@PathVariable("id") Long id) {
		try {
			Production production = productionService.getProductionById(id);
			return Response.getSuccessResponse(production, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/production/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> updateProductionById(@PathVariable("id") Long id, @RequestBody ProductionDto productionDto,
			HttpServletRequest request) {
		try {
			Production production = productionService.updateProductionById(id, productionDto, request);
			return Response.getSuccessResponse(production, "Production Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/production/{id}")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> deleteProductionById(@PathVariable("id") Long id) {
		try {
			productionService.deleteProductionById(id);
			return Response.getSuccessResponse(null, "Production Deleted Successfully", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/production-list")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getProductionList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<Production> production = productionService.getProductionList(dataFilter);
			return Response.getSuccessResponse(production, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}