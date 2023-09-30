package com.tm.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.Expense;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense, Long> {

	@Query("SELECT ex FROM Expense ex join Employee e on(ex.employee.id=e.id) where LOWER(e.name) like LOWER(:search)")
	Page<Expense> findByEmployeeLikeIgnoreCase(String search, PageRequest of);

}
