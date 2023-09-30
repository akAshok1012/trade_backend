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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.ShipmentCustomerListDto;
import com.tm.app.dto.ShipmentDto;
import com.tm.app.dto.ShipmentItemDto;
import com.tm.app.entity.OrderItem;
import com.tm.app.entity.Shipment;
import com.tm.app.entity.ShipmentDetails;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.service.OrderItemService;
import com.tm.app.service.ShipmentService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ShipmentController {

	@Autowired
	private OrderItemService orderItemService;

	@Autowired
	private ShipmentService shipmentService;

	@PostMapping("/shipment")
	@IsSuperAdminOrAdmin
	public APIResponse<?> saveShipment(@RequestBody ShipmentDto shipmentDto) {
		try {
			Shipment shipment = shipmentService.saveShipment(shipmentDto);
			return Response.getSuccessResponse(shipment, "Shipment Saved Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/shipments")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getShipments() {
		try {
			List<Shipment> shipments = shipmentService.getShipments();
			return Response.getSuccessResponse(shipments, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/shipment/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getShipmentById(@PathVariable("id") Long id) {
		try {
			Shipment shipment = shipmentService.getShipmentById(id);
			return Response.getSuccessResponse(shipment, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/shipment")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateShipment(@RequestBody ShipmentDto shipmentDto) {
		try {
			List<Shipment> shipment = shipmentService.updateShipment(shipmentDto);
			return Response.getSuccessResponse(shipment, "Shipment Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/shipment/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteShipmentById(@PathVariable("id") Long id) {
		try {
			shipmentService.deleteShipmentById(id);
			return Response.getSuccessResponse(null, "Shipment Deleted Successfully", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/shipments-salesId")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getShipmentBySalesId(@RequestParam("salesId") Integer salesId) {
		try {
			List<ShipmentItemDto> shipment = shipmentService.getShipmentBySalesId(salesId);
			return Response.getSuccessResponse(shipment, "success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/shipment-salesId")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getOrderItemBySalesId(@RequestParam("salesId") Integer salesId) {
		try {
			List<OrderItem> orders = orderItemService.getOrderItemBySalesId(salesId);
			return Response.getSuccessResponse(orders, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customer-shipment-salesId")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getCustomerShipmentBySalesId(@RequestParam("salesId") Integer salesId,
			@RequestParam("id") Long id) {
		try {
			List<ShipmentDetails> orders = shipmentService.getCustomerShipmentBySalesId(salesId, id);
			return Response.getSuccessResponse(orders, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/shipment-listing")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getShipmentListing(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<Shipment> shipment = shipmentService.getShipmentListing(dataFilter);
			return Response.getSuccessResponse(shipment, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/shipmentss-salesId")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getShipmentsBySalesId(@RequestParam(value = "salesId", required = false) Integer salesId,
			@ModelAttribute DataFilter dataFilter) {
		try {
			Page<ShipmentCustomerListDto> shipment = shipmentService.getShipmentsBySalesId(salesId, dataFilter);
			return Response.getSuccessResponse(shipment, "success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/shipment-listing-customer")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getShipmentListingWithCustomer(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<ShipmentCustomerListDto> shipment = shipmentService.getShipmentListingWithCustomer(dataFilter);
			return Response.getSuccessResponse(shipment, "Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}