package com.tm.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.EmployeeAttendance;

@Repository
public interface EmployeeAttendanceRepo extends JpaRepository<EmployeeAttendance, Long> {

	@Query("select ea from EmployeeAttendance ea join Employee e on (ea.employee=e.id) where lower(e.name) like lower(:search)")
	Page<EmployeeAttendance> getEmployeeAttendanceList(String search, PageRequest of);

}
