package com.tm.app.service;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.EmployeeLeaveManagerDto;
import com.tm.app.dto.EmployeeLeaveStatusDto;
import com.tm.app.entity.EmployeeLeaveManager;
import com.tm.app.enums.EmployeeLeaveStatus;
import com.tm.app.enums.LeaveType;

public interface EmployeeLeaveManagerService {

	public EmployeeLeaveManager applyEmployeeLeave(EmployeeLeaveManagerDto employeeLeaveManagerDto);

	public Page<EmployeeLeaveManager> getEmployeeLeaves(DataFilter dataFilter, Long id,
			EmployeeLeaveStatus employeeLeaveStatus, Date fromDate, Date toDate);

	public EmployeeLeaveManager getEmployeeLeaveById(Long id);

	public EmployeeLeaveManager approveEmployeeLeave(Long id, EmployeeLeaveManagerDto employeeLeaveManagerDto);

	public void deleteEmployeeLeaveById(Long id);

	public List<EmployeeLeaveManager> getEmployeeLeaveStatus(EmployeeLeaveStatus employeeLeaveStatus);

	public List<EmployeeLeaveManager> getLeaveType(LeaveType leaveType);

	public List<EmployeeLeaveManager> getLeaveByEmployeeId(Long id);

	public List<EmployeeLeaveManager> getLeaveByEmployee(String name, EmployeeLeaveStatus employeeLeaveStatus);

	public List<EmployeeLeaveStatusDto> getEmployeeLeaveStatus(Long id);

	public EmployeeLeaveManager getIsNotificationEnabled(Long id);

}