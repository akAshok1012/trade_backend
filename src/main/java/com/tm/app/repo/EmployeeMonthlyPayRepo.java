package com.tm.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.EmployeeMonthlyPay;

import jakarta.transaction.Transactional;

@Repository
public interface EmployeeMonthlyPayRepo extends JpaRepository<EmployeeMonthlyPay, Long> {

	@Transactional
	@Query(value = "Select generate_monthly_payslip();",nativeQuery = true)
	void getEmployeeMonthlyPay();

	@Transactional
	@Modifying
	@Query(value = "TRUNCATE TABLE fn_employee_monthly_pay RESTART IDENTITY", nativeQuery = true)
	void truncateTable();

	@Query("Select emp from EmployeeMonthlyPay emp where LOWER(emp.employee.name) like LOWER(:search)")
	Page<EmployeeMonthlyPay> getEmployeeMonthlyPayList(String search, PageRequest of);

}
