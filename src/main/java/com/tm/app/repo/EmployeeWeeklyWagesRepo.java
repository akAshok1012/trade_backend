package com.tm.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tm.app.entity.EmployeeWeeklyWages;

public interface EmployeeWeeklyWagesRepo extends JpaRepository<EmployeeWeeklyWages, Long> {

	Page<EmployeeWeeklyWages> findByEmployeeNameLikeIgnoreCase(String search, PageRequest of);

}
