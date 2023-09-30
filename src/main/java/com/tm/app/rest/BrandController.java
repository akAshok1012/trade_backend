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

import com.tm.app.dto.BrandDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.entity.Brand;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrEmployee;
import com.tm.app.service.BrandService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.APIResponseConstants;
import com.tm.app.utils.Response;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class BrandController {

	@Autowired
	private BrandService brandService;

	@GetMapping("/brands")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getBrands() {
		log.info("[BrandController] getBrands starts");
		try {
			List<Brand> brand = brandService.getBrands();
			return Response.getSuccessResponse(brand,APIResponseConstants.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
		} catch (Exception e) {
			log.error("[BrandController] getBrands failed",e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/brand")
	@IsSuperAdminOrAdmin
	public APIResponse<?> saveBrand(@RequestBody BrandDto brandDto) {
		log.info("[BrandController] saveBrand starts");
		try {
			Brand brand = brandService.saveBrand(brandDto);
			return Response.getSuccessResponse(brand,String.format(APIResponseConstants.CREATION_SUCCESS_MESSAGE, brand.getName()),
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("[BrandController] saveBrand failed",e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/brand/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getBrandById(@PathVariable("id") Long id) {
		log.info("[BrandController] getBrandById starts");
		try {
			Brand brand = brandService.getBrandById(id);
			return Response.getSuccessResponse(brand,APIResponseConstants.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
		} catch (Exception e) {
			log.info("[BrandController] getBrandById failed",e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/brand/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateBrand(@PathVariable Long id, @RequestBody BrandDto brandDto) {
		try {
			Brand brand = brandService.updateBrand(id, brandDto);
			return Response.getSuccessResponse(brand,String.format(APIResponseConstants.UPDATED_SUCCESS_MESSAGE,brand.getName()),
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/brand/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteBrandById(@PathVariable("id") Long id) {
		try {
			brandService.deleteBrandById(id);
			return Response.getSuccessResponse(null,APIResponseConstants.DELETE_RESPONSE_MESSAGE, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/brand-list")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getBrandList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<Brand> brand = brandService.getBrandList(dataFilter);
			return Response.getSuccessResponse(brand,APIResponseConstants.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}