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

import com.tm.app.dto.ContractorDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.entity.Contractor;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.service.ContractorService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.APIResponseConstants;
import com.tm.app.utils.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ContractorController {

	@Autowired
	private ContractorService contractService;

	@PostMapping("/contractor")
	@IsSuperAdminOrAdmin
	public APIResponse<?> saveContractor(@RequestBody ContractorDto contractorDto) {
		log.info("[ContractorController] saveContractor starts ");
		try {
			Contractor contractor = contractService.saveContractor(contractorDto);
			return Response.getSuccessResponse(contractor,
					"Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[ContractorController] saveContractor failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/contractors")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getContractors() {
		log.info("[ContractorController] getContractors starts ");
		try {
			List<Contractor> contractor = contractService.getContractors();
			return Response.getSuccessResponse(contractor, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("[ContractorController] getContractors failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/contractor/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getContractorById(@PathVariable("id") Long id) {
		log.info("[ContractorController] getContractorById starts ");
		try {
			Contractor contractor = contractService.getContractorById(id);
			return Response.getSuccessResponse(contractor,"updated Successfully",
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("[ContractorController] getContractorById failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/contractor/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateContractor(@PathVariable Long id, @RequestBody ContractorDto contractorDto) {
		log.info("[ContractorController] updateContractor starts ");
		try {
			Contractor contractor = contractService.updateContractor(id, contractorDto);
			return Response.getSuccessResponse(contractor,
					String.format(APIResponseConstants.UPDATED_SUCCESS_MESSAGE, contractor.getName()), HttpStatus.OK);
		} catch (Exception e) {
			log.error("[ContractorController] updateContractor failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/contractor/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteContractorById(@PathVariable("id") Long id) {
		log.info("[ContractorController] deleteContractorById starts ");
		try {
			contractService.deleteContractorById(id);
			return Response.getSuccessResponse(null, APIResponseConstants.DELETE_RESPONSE_MESSAGE,
					HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			log.error("[ContractorController] deleteContractorById failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/contractor-list")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getContractorList(@ModelAttribute DataFilter dataFilter) {
		log.info("[ContractorController] getContractorList starts ");
		try {
			Page<Contractor> contractor = contractService.getContractorList(dataFilter);
			return Response.getSuccessResponse(contractor, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("[ContractorController] getContractorList failed", e);
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}