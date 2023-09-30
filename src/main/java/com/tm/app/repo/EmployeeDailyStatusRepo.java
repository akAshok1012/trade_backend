package com.tm.app.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.Employee;
import com.tm.app.entity.EmployeeDailyStatus;

@Repository
public interface EmployeeDailyStatusRepo extends JpaRepository<EmployeeDailyStatus, Long> {

	@Query("select eds FROM EmployeeDailyStatus eds join Employee e on(e.id = eds.employee) where LOWER(e.name) like LOWER(:search)")
	Page<EmployeeDailyStatus> getEmployeeDailyStatusBySearchString(String search, PageRequest of);

	Page<EmployeeDailyStatus> findByEmployee(Employee employee, PageRequest of);

	@Query("select eds FROM EmployeeDailyStatus eds  where eds.employee =:employee AND eds.date BETWEEN TO_DATE(:fromDate, 'YYYY-MM-DD') and TO_DATE(:toDate, 'YYYY-MM-DD')")
	Page<EmployeeDailyStatus> getEmployeeDailyStatusByEmployee(Employee employee, String fromDate, String toDate,
			PageRequest of);

	@Query("select eds FROM EmployeeDailyStatus eds join Employee e on(e.id = eds.employee) where LOWER(e.name) like LOWER(:search) AND eds.date BETWEEN TO_DATE(:fromDate, 'YYYY-MM-DD') and TO_DATE(:toDate, 'YYYY-MM-DD')")
	Page<EmployeeDailyStatus> getAllEmployeeDailyStatusByFromAndToDate(String search,PageRequest of, String fromDate, String toDate);

	Page<EmployeeDailyStatus> findByDateBetween(String fromDate, String toDate, PageRequest of);

	List<EmployeeDailyStatus> findByEmployee(Employee employee);

}
