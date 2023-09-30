package com.tm.app.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.ExpenseDto;
import com.tm.app.entity.Expense;
import com.tm.app.repo.ExpenseRepo;
import com.tm.app.service.ExpenseService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private ExpenseRepo expenseRepo;

	@Override
	public List<Expense> getExpenses() {
		return expenseRepo.findAll();
	}

	@Override
	@Transactional
	public Expense saveExpense(ExpenseDto expenseDto) {

		Expense expense = new Expense();
		try {
			BeanUtils.copyProperties(expenseDto, expense);
			expense = expenseRepo.save(expense);
		} catch (Exception e) {
			log.error("[EXPENSE] adding expense failed", e);
			throw new RuntimeException("Adding expense failed");
		}
		return expense;
	}

	@Override
	public Expense getExpenseById(Long id) {
		return expenseRepo.findById(id).orElseThrow();
	}

	@Override
	@Transactional
	public void deleteItemMasterById(Long id) {
		try {
			expenseRepo.deleteById(id);
		} catch (Exception e) {
			log.error("[EXPENSE] deleting expense failed", e);
			throw new RuntimeException("Deleting expense failed");
		}
	}

	@Override
	@Transactional
	public Expense updateExpense(Long id, ExpenseDto expenseDto) {
		Expense expense = expenseRepo.findById(id).orElseThrow();
		BeanUtils.copyProperties(expenseDto, expense);
		return expenseRepo.save(expense);
	}

	@Override
	public Page<Expense> getExpenseList(DataFilter dataFilter) {
		return expenseRepo.findByEmployeeLikeIgnoreCase(dataFilter.getSearch(), PageRequest.of(dataFilter.getPage(),
				dataFilter.getSize(), Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}
}