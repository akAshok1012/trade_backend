package com.tm.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.EmployeeSwipeEntry;

@Repository
public interface EmployeeSwipeEntryRepo extends JpaRepository<EmployeeSwipeEntry, Long> {

	@Query("select es from EmployeeSwipeEntry es join Employee e on (es.employee=e.id) where lower(e.name) like lower(:search)")
	Page<EmployeeSwipeEntry> getEmployeeSwipeEntryList(String search, PageRequest of);

}
