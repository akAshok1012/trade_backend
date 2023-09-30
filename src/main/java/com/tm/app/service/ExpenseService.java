package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.ExpenseDto;
import com.tm.app.entity.Expense;

public interface ExpenseService {

	public List<Expense> getExpenses();

	public Expense saveExpense(ExpenseDto expenseDto);

	public Expense getExpenseById(Long id);

	public void deleteItemMasterById(Long id);

	public Expense updateExpense(Long id, ExpenseDto expenseDto);

	public Page<Expense> getExpenseList(DataFilter dataFilter);

}