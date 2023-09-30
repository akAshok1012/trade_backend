package com.tm.app.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.ExpenseCategoryDto;
import com.tm.app.entity.ExpenseCategory;
import com.tm.app.repo.ExpenseCategoryRepo;
import com.tm.app.service.ExpenseCategoryService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ExpenseCategoryServiceImpl implements ExpenseCategoryService {

	@Autowired
	private ExpenseCategoryRepo expenseCategoryRepo;

	@Override
	public List<ExpenseCategory> getExpenseCategory() {
		return expenseCategoryRepo.findAll();
	}

	@Override
	@Transactional
	public ExpenseCategory saveExpenseCategory(ExpenseCategoryDto expenseCategoryDto) {

		ExpenseCategory expenseCategory = new ExpenseCategory();
		try {
			if (expenseCategoryRepo.existsByCategoryNameEqualsIgnoreCase(expenseCategoryDto.getCategoryName())) {
				throw new RuntimeException("Category Name already exists!");
			}
			BeanUtils.copyProperties(expenseCategoryDto, expenseCategory);
			expenseCategory = expenseCategoryRepo.save(expenseCategory);
		} catch (Exception e) {
			log.error("[EXPENSE] adding expense category failed", e);
			throw new RuntimeException(e.getMessage());
		}
		return expenseCategory;
	}

	@Override
	public ExpenseCategory getExpenseCategoryById(Long id) {
		return expenseCategoryRepo.findById(id).orElseThrow();
	}

	@Override
	@Transactional
	public void deleteExpenseCategoryById(Long id) {
		try {
			expenseCategoryRepo.deleteById(id);
		} catch (Exception e) {
			log.error("[EXPENSE] deleting expense category failed", e);
			throw new RuntimeException("Deleting expense category failed");
		}
	}

	@Override
	@Transactional
	public ExpenseCategory updateExpenseCategory(Long id, ExpenseCategoryDto expenseCategoryDto) {
		ExpenseCategory expenseCategory = expenseCategoryRepo.findById(id).orElseThrow();
		try {
			if (expenseCategoryRepo.existsByCategoryNameEqualsIgnoreCase(expenseCategoryDto.getCategoryName())
					&& !expenseCategory.getCategoryName().equalsIgnoreCase(expenseCategoryDto.getCategoryName())) {
				throw new RuntimeException("Category Name already exists!");
			}
			expenseCategory.setCategoryName(expenseCategoryDto.getCategoryName());
			expenseCategory = expenseCategoryRepo.save(expenseCategory);
		} catch (Exception e) {
			log.error("[EXPENSE] updating expense category failed", e);
			throw new RuntimeException(e.getMessage());
		}
		return expenseCategory;
	}

	@Override
	public Page<ExpenseCategory> getExpenseCategoryList(DataFilter dataFilter) {
		return expenseCategoryRepo.findByCategoryNameLikeIgnoreCase(dataFilter.getSearch(),
				PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
						Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}
}