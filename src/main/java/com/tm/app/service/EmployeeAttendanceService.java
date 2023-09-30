package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.EmployeeAttendanceDto;
import com.tm.app.entity.EmployeeAttendance;

public interface EmployeeAttendanceService {

	EmployeeAttendance saveEmployeeAttendance(EmployeeAttendanceDto employeeAttendanceDto);

	List<EmployeeAttendance> getEmployeeAttendance();

	EmployeeAttendance getEmployeeAttendanceById(Long id);

	EmployeeAttendance updateEmployeeAttendance(Long id, EmployeeAttendanceDto employeeAttendanceDto);

	void deleteEmployeeAttendanceById(Long id);

	Page<EmployeeAttendance> getEmployeeAttendanceList(DataFilter dataFilter);

}
