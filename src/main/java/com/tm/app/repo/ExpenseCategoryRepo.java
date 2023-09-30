package com.tm.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.ExpenseCategory;

@Repository
public interface ExpenseCategoryRepo extends JpaRepository<ExpenseCategory, Long> {

	Page<ExpenseCategory> findByCategoryNameLikeIgnoreCase(String search, PageRequest of);

	boolean existsByCategoryNameEqualsIgnoreCase(String categoryName);

}
