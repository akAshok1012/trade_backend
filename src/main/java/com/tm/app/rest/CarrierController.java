package com.tm.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tm.app.dto.CarrierDto;
import com.tm.app.entity.Carrier;
import com.tm.app.service.CarrierService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class CarrierController {

	@Autowired
	private CarrierService carrierService;

	@PostMapping("/carrier")
	public APIResponse<?> saveCarrier(@RequestBody CarrierDto carrierDto) {
		try {
			Carrier carrier = carrierService.saveCarrier(carrierDto);
			return Response.getSuccessResponse(carrier,"'" + carrier.getCarrierCompany()+ "'" +" Created Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/carriers")
	public APIResponse<?> getCarriers() {
		try {
			List<Carrier> carriers = carrierService.getCarriers();
			return Response.getSuccessResponse(carriers, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/carrier/{id}")
	public APIResponse<?> getCarrierById(@PathVariable("id") Long id) {
		try {
			Carrier carrier = carrierService.getCarrierById(id);
			return Response.getSuccessResponse(carrier,"'" + carrier.getCarrierCompany()+ "'" +" Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/carrier/{id}")
	public APIResponse<?> updateCarrier(@PathVariable Long id, @RequestBody CarrierDto carrierDto) {
		try {
			Carrier carrier = carrierService.updateCarrier(id, carrierDto);
			return Response.getSuccessResponse(carrier, "Carrier Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/carrier/{id}")
	public APIResponse<?> deleteCarrierById(@PathVariable("id") Long id) {
		try {
			carrierService.deleteCarrierById(id);
			return Response.getSuccessResponse(null, "Carrier Deleted Successfully", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}