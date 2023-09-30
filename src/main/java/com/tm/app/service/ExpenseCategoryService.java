package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.ExpenseCategoryDto;
import com.tm.app.entity.ExpenseCategory;

public interface ExpenseCategoryService {

	public List<ExpenseCategory> getExpenseCategory();

	public ExpenseCategory saveExpenseCategory(ExpenseCategoryDto expenseCategoryDto);

	public ExpenseCategory getExpenseCategoryById(Long id);

	public void deleteExpenseCategoryById(Long id);

	public ExpenseCategory updateExpenseCategory(Long id, ExpenseCategoryDto expenseCategoryDto);

	public Page<ExpenseCategory> getExpenseCategoryList(DataFilter dataFilter);

}