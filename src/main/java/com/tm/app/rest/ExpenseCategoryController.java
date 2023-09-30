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
import com.tm.app.dto.ExpenseCategoryDto;
import com.tm.app.entity.ExpenseCategory;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.service.ExpenseCategoryService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class ExpenseCategoryController {

	@Autowired
	private ExpenseCategoryService expenseCategoryService;

	@PostMapping("/expense-category")
	@IsSuperAdminOrAdmin
	public APIResponse<?> saveExpenseCategory(@RequestBody ExpenseCategoryDto expenseCategoryDto) {
		try {
			ExpenseCategory expenseCategory = expenseCategoryService.saveExpenseCategory(expenseCategoryDto);
			return Response.getSuccessResponse(expenseCategory, "ExpenseCategory Saved Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/expense-categories")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getExpenseCategory(@ModelAttribute DataFilter dataFilter) {
		try {
			List<ExpenseCategory> expenseCategory = expenseCategoryService.getExpenseCategory();
			return Response.getSuccessResponse(expenseCategory, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/expense-category/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getExpenseCategoryById(@PathVariable("id") Long id) {
		try {
			ExpenseCategory expenseCategory = expenseCategoryService.getExpenseCategoryById(id);
			return Response.getSuccessResponse(expenseCategory, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/expense-category/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateExpenseCategory(@PathVariable Long id,
			@RequestBody ExpenseCategoryDto expenseCategoryDto) {
		try {
			ExpenseCategory expenseCategory = expenseCategoryService.updateExpenseCategory(id, expenseCategoryDto);
			return Response.getSuccessResponse(expenseCategory, "ExpenseCategory Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/expense-category/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteExpenseCategoryById(@PathVariable("id") Long id) {
		try {
			expenseCategoryService.deleteExpenseCategoryById(id);
			return Response.getSuccessResponse(null, "ExpenseCategory Deleted Successfully", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/expense-category-list")
	public APIResponse<?> getExpenseCategoryList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<ExpenseCategory> expenseCategory = expenseCategoryService.getExpenseCategoryList(dataFilter);
			return Response.getSuccessResponse(expenseCategory, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}