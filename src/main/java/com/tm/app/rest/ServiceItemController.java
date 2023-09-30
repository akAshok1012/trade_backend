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
import com.tm.app.dto.ServiceItemDto;
import com.tm.app.entity.ServiceItem;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.service.ServiceItemService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.APIResponseConstants;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ServiceItemController {

	@Autowired
	private ServiceItemService serviceItemService;
	
	@PostMapping("/service-item")
	@IsSuperAdminOrAdmin
	public APIResponse<?> saveServiceItem(@RequestBody ServiceItemDto serviceItemDto) {
		try {
			ServiceItem serviceItem = serviceItemService.saveServiceItem(serviceItemDto);
			return Response.getSuccessResponse(serviceItem, String.format(APIResponseConstants.CREATION_SUCCESS_MESSAGE, serviceItem.getItemName()), HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/service-items")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getServiceItems() {
		try {
			List<ServiceItem> serviceItems = serviceItemService.getServiceItems();
			return Response.getSuccessResponse(serviceItems, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/service-item/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getServiceItemById(@PathVariable("id") Long id) {
		try {
			ServiceItem serviceItem = serviceItemService.getServiceItemById(id);
			return Response.getSuccessResponse(serviceItem, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/service-item/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateServiceItem(@PathVariable Long id,
			@RequestBody ServiceItemDto serviceItemDto) {
		try {
			ServiceItem serviceItem = serviceItemService.updateServiceItem(id, serviceItemDto);
			return Response.getSuccessResponse(serviceItem, String.format(APIResponseConstants.UPDATED_SUCCESS_MESSAGE, serviceItem.getItemName()), HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/service-item/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteServiceItemById(@PathVariable("id") Long id) {
		try {
			serviceItemService.deleteServiceItemById(id);
			return Response.getSuccessResponse(null, APIResponseConstants.DELETE_RESPONSE_MESSAGE, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/service-item-list")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getServiceItemList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<ServiceItem> serviceItems = serviceItemService.getServiceItemList(dataFilter);
			return Response.getSuccessResponse(serviceItems, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}