package com.tm.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.ShipmentCustomerUserStatusDto;
import com.tm.app.dto.ShipmentHistoryDto;
import com.tm.app.dto.ShipmentStatusDto;
import com.tm.app.dto.ShipmentStatusSalesIdDto;
import com.tm.app.entity.ShipmentDetails;
import com.tm.app.enums.ShipmentStatus;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrCustomer;
import com.tm.app.service.ShipmentDetailsService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ShipmentDetailsController {

	@Autowired
	private ShipmentDetailsService shipmentDetailsService;

	@GetMapping("/shipment-details")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getShipmentDetails() {
		try {
			List<ShipmentDetails> orders = shipmentDetailsService.getShipmentDetails();
			return Response.getSuccessResponse(orders, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/shipment-status")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getShipmentStatus(@RequestParam ShipmentStatus shipmentStatus,
			@ModelAttribute DataFilter dataFilter) {
		try {
			Page<ShipmentStatusDto> shipmentStatusDto = shipmentDetailsService.getShipmentStatus(shipmentStatus,
					dataFilter);
			return Response.getSuccessResponse(shipmentStatusDto, "success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/shipment-userId")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> getShipmentUserId(@RequestParam("id") Long id,
			@RequestParam("shipmentStatus") ShipmentStatus shipmentStatus, @ModelAttribute DataFilter dataFilter) {
		try {
			Page<ShipmentCustomerUserStatusDto> shipmentCustomerDto = shipmentDetailsService.getShipmentUserId(id,
					shipmentStatus, dataFilter);
			return Response.getSuccessResponse(shipmentCustomerDto, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/shipment-details-listing")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getShipmentDetailsListing(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<ShipmentDetails> shipmentDetails = shipmentDetailsService.getShipmentDetailsListing(dataFilter);
			return Response.getSuccessResponse(shipmentDetails, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/shipment-history-salesid")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> getShipmentHistoryBySalesId(@RequestParam("salesId") Integer salesId) {
		try {
			List<List<ShipmentHistoryDto>> shipmentHistoryDto = shipmentDetailsService
					.getShipmentHistoryBySalesId(salesId);
			return Response.getSuccessResponse(shipmentHistoryDto, "success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/shipment-salesid-status")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> getShipmentStatusSalesId(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "salesId", required = false) Integer salesId,
			@RequestParam("shipmentStatus") ShipmentStatus shipmentStatus, @ModelAttribute DataFilter dataFilter) {
		try {
			Page<ShipmentStatusSalesIdDto> shipmentCustomerDto = shipmentDetailsService.getShipmentStatusSalesId(id,
					salesId, shipmentStatus, dataFilter);
			return Response.getSuccessResponse(shipmentCustomerDto, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}