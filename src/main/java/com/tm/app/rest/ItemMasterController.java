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
import com.tm.app.dto.ItemMasterDto;
import com.tm.app.dto.ItemMasterIdAndPriceDto;
import com.tm.app.dto.ItemMasterPriceDto;
import com.tm.app.entity.ItemMaster;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrCustomer;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrCustomerOrEmployee;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrEmployee;
import com.tm.app.service.ItemMasterService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class ItemMasterController {

	@Autowired
	private ItemMasterService itemMasterService;

	@PostMapping("/item-master")
	@IsSuperAdminOrAdmin
	public APIResponse<?> saveItemMaster(@RequestBody ItemMasterDto itemMasterDto) {
		try {
			ItemMaster itemManster = itemMasterService.saveItemMaster(itemMasterDto);
			return Response.getSuccessResponse(itemManster,
					"'" + itemManster.getItemName() + "'" + " Created Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/item-masters")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getItemMasters() {
		try {
			List<ItemMaster> itemMaster = itemMasterService.getItemMasters();
			return Response.getSuccessResponse(itemMaster, "success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/item-master-price")
	@IsSuperAdminOrAdminOrCustomerOrEmployee
	public APIResponse<?> getItemMasterPriceData() {
		try {
			List<ItemMasterPriceDto> itemMaster = itemMasterService.getItemMasterPriceData();
			return Response.getSuccessResponse(itemMaster, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/item-master/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getItemMasterById(@PathVariable("id") Long id) {
		try {
			ItemMaster itemMaster = itemMasterService.getItemMasterById(id);
			return Response.getSuccessResponse(itemMaster, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/item-master/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateItemMaster(@PathVariable Long id, @RequestBody ItemMasterDto itemMasterDto) {
		try {
			ItemMaster itemMaster = itemMasterService.updateItemMaster(id, itemMasterDto);
			return Response.getSuccessResponse(itemMaster,
					"'" + itemMaster.getItemName() + "'" + "  Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/item-master/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteItemMasterById(@PathVariable("id") Long id) {
		try {
			itemMasterService.deleteItemMasterById(id);
			return Response.getSuccessResponse(null, "Item Deleted Successfully", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/item-id-price")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getItemMasterIdAndPrice() {
		try {
			List<ItemMasterIdAndPriceDto> idAndPriceDto = itemMasterService.getItemMasterIdAndPrice();
			return Response.getSuccessResponse(idAndPriceDto, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/item-brands")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getItemBrands(@RequestParam("id") Long brandId) {
		try {
			List<ItemMaster> itemMaster = itemMasterService.getItemBrands(brandId);
			return Response.getSuccessResponse(itemMaster, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/item-master-cart-list")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> getItemDetailsBySearch(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<ItemMasterPriceDto> itemMaster = itemMasterService.getItemDetailsBySearch(dataFilter);
			return Response.getSuccessResponse(itemMaster, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/item-master-list")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> getItemMasterList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<ItemMaster> itemMaster = itemMasterService.getItemMasterList(dataFilter);
			return Response.getSuccessResponse(itemMaster, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}