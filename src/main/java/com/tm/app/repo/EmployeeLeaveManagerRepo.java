package com.tm.app.repo;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tm.app.dto.EmployeeLeaveStatusDto;
import com.tm.app.entity.Employee;
import com.tm.app.entity.EmployeeLeaveManager;
import com.tm.app.enums.EmployeeLeaveStatus;
import com.tm.app.enums.LeaveType;

@Repository
public interface EmployeeLeaveManagerRepo extends JpaRepository<EmployeeLeaveManager, Long> {

	List<EmployeeLeaveManager> findByEmployee(Employee employee);

	List<EmployeeLeaveManager> findByEmployeeLeaveStatusOrderByUpdatedAtDesc(EmployeeLeaveStatus employeeLeaveStatus);

	List<EmployeeLeaveManager> findByLeaveTypeOrderByUpdatedAtDesc(LeaveType leaveType);

	List<EmployeeLeaveManager> findLeaveByEmployeeId(Long id);

	List<EmployeeLeaveManager> findLeaveByEmployeeAndEmployeeLeaveStatus(Employee employee,
			EmployeeLeaveStatus employeeLeaveStatus);

	@Query("select elm FROM EmployeeLeaveManager elm join Employee e on(e.id = elm.employee) where LOWER(e.name) like LOWER(:search) AND (DATE(elm.startDate) <= :fromDate AND DATE(elm.endDate) >= :toDate) AND elm.employeeLeaveStatus =:employeeLeaveStatus")
	Page<EmployeeLeaveManager> getEmployeeLeaveSearch(PageRequest pageRequest, Date fromDate, Date toDate,
			String search, EmployeeLeaveStatus employeeLeaveStatus);

	@Query("select elm FROM EmployeeLeaveManager elm  join Employee e on(e.id = elm.employee) where LOWER(e.name) like LOWER(:search) AND elm.employeeLeaveStatus =:employeeLeaveStatus ")
	Page<EmployeeLeaveManager> getEmployeeLeaveStatusWithSearch(String search, PageRequest of,
			EmployeeLeaveStatus employeeLeaveStatus);

	@Query("select elm FROM EmployeeLeaveManager elm where  elm.employee=:employee AND elm.employeeLeaveStatus =:employeeLeaveStatus ")
	Page<EmployeeLeaveManager> getEmployeeLeaves(Employee employee, EmployeeLeaveStatus employeeLeaveStatus,
			PageRequest of);

	@Query("select elm FROM EmployeeLeaveManager elm  join Employee e on(e.id = elm.employee) where LOWER(e.name) like LOWER(:search) AND DATE(elm.updatedAt) BETWEEN TO_DATE(:fromDate,'YYYY-MM-DD') and TO_DATE(:toDate,'YYYY-MM-DD') AND elm.employeeLeaveStatus =:employeeLeaveStatus ")
	Page<EmployeeLeaveManager> getEmployeeLeaveDataUsingDateFilter(String search, PageRequest of,
			EmployeeLeaveStatus employeeLeaveStatus, String fromDate, String toDate);

	@Query("SELECT elm FROM EmployeeLeaveManager elm WHERE elm.employee = :employee AND (DATE(elm.startDate) <= :fromDate AND DATE(elm.endDate) >= :toDate)AND elm.employeeLeaveStatus = :employeeLeaveStatus")
	Page<EmployeeLeaveManager> getEmployeeLeavesWithDateFilter(Employee employee,
			EmployeeLeaveStatus employeeLeaveStatus, PageRequest of, Date fromDate, Date toDate);

	@Query("select new com.tm.app.dto.EmployeeLeaveStatusDto(elm.id as id,elm.employee as employee,elm.employeeLeaveStatus as employeeLeaveStatus,elm.startDate as startDate,elm.endDate as endDate) from EmployeeLeaveManager elm join Employee e on(e.id=elm.employee) join User u on(u.userId=e.id) where u.id=:id and elm.employeeLeaveStatus in ('APPROVED','REJECTED') and isNotificationEnabled=false")
	List<EmployeeLeaveStatusDto> getEmployeeLeaveStatus(Long id);

	boolean existsByEmployeeAndStartDateAndEndDate(Employee employee, Date startDate, Date endDate);

	@Query("select elm FROM EmployeeLeaveManager elm join Employee e on(e.id = elm.employee) where e.id=:id AND TO_DATE(:compensationDate,'YYYY-MM-DD') BETWEEN startDate and endDate")
	Optional<EmployeeLeaveManager> getCompensationDateBetweenStartAndEndDate(Long id, Date compensationDate);

	Optional<EmployeeLeaveManager> findByEmployeeAndCompensationDate(Employee employee, Date compensationDate);

	@Query("select elm FROM EmployeeLeaveManager elm join Employee e on(e.id = elm.employee) where e.id=:id AND compensationDate between TO_DATE(:startDate,'YYYY-MM-DD') and TO_DATE(:endDate,'YYYY-MM-DD')")
	Optional<EmployeeLeaveManager> getCompensationDateBetweenStartAndEndDates(Long id, Date startDate, Date endDate);

	boolean existsByEmployeeAndStartDate(Employee employee, Date startDate);

	boolean existsByEmployeeAndEndDate(Employee employee, Date endDate);

	@Query("select el from EmployeeLeaveManager el where el.employee =:employee and TO_DATE(:date,'YYYY-MM-DD')  between el.startDate and el.endDate")
	Optional<EmployeeLeaveManager> getByEmployeeAndDateBetween(@Param("employee") Employee employee,
			@Param("date") LocalDate date);

}