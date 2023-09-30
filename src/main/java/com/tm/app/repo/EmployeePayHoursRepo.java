package com.tm.app.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tm.app.entity.EmployeePayHours;

public interface EmployeePayHoursRepo extends JpaRepository<EmployeePayHours, Long> {

	Page<EmployeePayHours> findByEmployeeNameLikeIgnoreCase(String search, PageRequest of);

	@Query(value = "SELECT totalPay,totalHoursWorked FROM calculate_employee_pay( :id, :fromDate, :toDate)", nativeQuery = true)
	List<Object[]> getEmployeePayHoursFilter(@Param("id") Long id, @Param("fromDate") Date fromDate,
			@Param("toDate") Date toDate);

}
