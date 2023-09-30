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
import com.tm.app.dto.ItemCategoryDto;
import com.tm.app.entity.ItemCategory;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.service.ItemCategoryService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class ItemCategoryController {

	@Autowired
	private ItemCategoryService itemCategoryService;

	@PostMapping("/item-category")
	@IsSuperAdminOrAdmin
	public APIResponse<?> saveItemCategory(@RequestBody ItemCategoryDto itemCategoryDto) {
		try {
			ItemCategory itemCategory = itemCategoryService.saveItemCategory(itemCategoryDto);
			return Response.getSuccessResponse(itemCategory,"'"+ itemCategory.getCategoryName()+"'"+" Created Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/item-categories")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getItemCategories() {
		try {
			List<ItemCategory> itemCategory = itemCategoryService.getItemCategories();
			return Response.getSuccessResponse(itemCategory, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/item-category/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getItemCategoryById(@PathVariable("id") Long id) {
		try {
			ItemCategory itemCategory = itemCategoryService.getItemCategoryById(id);
			return Response.getSuccessResponse(itemCategory, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/item-category/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateItemCategory(@PathVariable Long id, @RequestBody ItemCategoryDto itemCategoryDto) {
		try {
			ItemCategory itemCategory = itemCategoryService.updateItemCategory(id, itemCategoryDto);
			return Response.getSuccessResponse(itemCategory,"'"+itemCategory.getCategoryName()+"'"+" Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/item-category/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteItemCategoryById(@PathVariable("id") Long id) {
		try {
			itemCategoryService.deleteItemCategoryById(id);
			return Response.getSuccessResponse(null, "ItemCategory Deleted Successfully", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/item-category-list")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getItemCategoryList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<ItemCategory> itemCategory = itemCategoryService.getItemCategoryList(dataFilter);
			return Response.getSuccessResponse(itemCategory, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}