package com.tm.app.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.Employee;
import com.tm.app.entity.EmployeePay;

@Repository
public interface EmployeePayRepo extends JpaRepository<EmployeePay, Long> {

	List<EmployeePay> findByEmployee(Employee employee);

	@Query("Select ep from EmployeePay ep join Employee e on (e.id=ep.employee) where LOWER(e.name) like LOWER(:search)")
	Page<EmployeePay> getEmployeePayList(String search, PageRequest of);

	boolean existsByEmployee(Employee employee);
}