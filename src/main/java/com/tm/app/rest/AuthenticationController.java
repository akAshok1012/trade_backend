package com.tm.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tm.app.dto.AuthenticationRequest;
import com.tm.app.dto.AuthenticationResponse;
import com.tm.app.dto.DataFilter;
import com.tm.app.dto.LeadGenerationDto;
import com.tm.app.entity.Brand;
import com.tm.app.entity.ItemMaster;
import com.tm.app.entity.LeadGeneration;
import com.tm.app.service.BrandService;
import com.tm.app.service.ItemMasterService;
import com.tm.app.service.LeadGenerationService;
import com.tm.app.service.impl.AuthenticationService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.APIResponseConstants;
import com.tm.app.utils.Response;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService service;

    @Autowired
    private BrandService brandService;

    @Autowired
    private ItemMasterService itemMasterService;

    @Autowired
    private LeadGenerationService leadGenerationService;

    @PostMapping("/authenticate")
    public APIResponse<?> authenticate(@RequestBody AuthenticationRequest request)
	    throws JsonProcessingException {
	try {
	    AuthenticationResponse response = service.authenticate(request);
	    return Response.getSuccessResponse(response, "Authenticated Successfully", HttpStatus.OK);
	} catch (Exception e) {
	    return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}
    }

    @GetMapping("/brands")
    public APIResponse<?> getBrands() {
	log.info("[BrandController] getBrands starts");
	try {
	    List<Brand> brand = brandService.getBrands();
	    return Response.getSuccessResponse(brand, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
	} catch (Exception e) {
	    log.error("[BrandController] getBrands failed", e);
	    return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}
    }

    @GetMapping("/item-master-list")
    public APIResponse<?> getItemMasterList(@ModelAttribute DataFilter dataFilter) {
	try {
	    Page<ItemMaster> itemMaster = itemMasterService.getItemMasterList(dataFilter);
	    return Response.getSuccessResponse(itemMaster, "success", HttpStatus.OK);
	} catch (Exception e) {
	    return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}
    }

    @PostMapping("/lead-generation")
    public APIResponse<?> saveLeadGeneration(@RequestBody LeadGenerationDto leadGenerationDto) {
	try {
	    LeadGeneration leadGeneration = leadGenerationService.saveLeadGeneration(leadGenerationDto);
	    return Response.getSuccessResponse(leadGeneration, "Lead Generation Created Successfully", HttpStatus.OK);
	} catch (Exception e) {
	    e.printStackTrace();
	    return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}
    }
}