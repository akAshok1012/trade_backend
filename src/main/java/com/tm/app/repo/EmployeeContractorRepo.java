package com.tm.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.EmployeeContractor;

@Repository
public interface EmployeeContractorRepo extends JpaRepository<EmployeeContractor, Long> {

	@Query("SELECT ec FROM EmployeeContractor ec where LOWER(ec.firstName) like LOWER(:search) OR LOWER(ec.department) like LOWER(:search)")
	Page<EmployeeContractor> findByFirstNameOrDepartment(String search, PageRequest of);

}