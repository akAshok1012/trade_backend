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
import com.tm.app.dto.ExpenseDto;
import com.tm.app.entity.Expense;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.service.ExpenseService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;

	@PostMapping("/expense")
	@IsSuperAdminOrAdmin
	public APIResponse<?> saveExpense(@RequestBody ExpenseDto expenseDto) {
		try {
			Expense expense = expenseService.saveExpense(expenseDto);
			return Response.getSuccessResponse(expense, "Expense Saved Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/expenses")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getExpenses() {
		try {
			List<Expense> expenses = expenseService.getExpenses();
			return Response.getSuccessResponse(expenses, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/expense/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getExpenseById(@PathVariable("id") Long id) {
		try {
			Expense expense = expenseService.getExpenseById(id);
			return Response.getSuccessResponse(expense, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/expense/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateExpense(@PathVariable Long id, @RequestBody ExpenseDto expenseDto) {
		try {
			Expense expense = expenseService.updateExpense(id, expenseDto);
			return Response.getSuccessResponse(expense, "Expense Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/expense/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteItemMasterById(@PathVariable("id") Long id) {
		try {
			expenseService.deleteItemMasterById(id);
			return Response.getSuccessResponse(null, "Expense Deleted Successfully", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/expense-list")
	public APIResponse<?> getExpenseList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<Expense> expense = expenseService.getExpenseList(dataFilter);
			return Response.getSuccessResponse(expense, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}