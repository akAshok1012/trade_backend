package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.EmployeeDailyStatusDto;
import com.tm.app.entity.EmployeeDailyStatus;

public interface EmployeeDailyStatusService {

	public EmployeeDailyStatus saveEmployeeDailyStatus(EmployeeDailyStatusDto employeeDailyStatusDto);

	public List<EmployeeDailyStatus> getEmployeeDailyStatus();

	public EmployeeDailyStatus getEmployeeDailyStatusById(Long id);

	public EmployeeDailyStatus updateEmployeeDailyStatus(Long id, EmployeeDailyStatusDto employeeDailyStatusDto);

	public void deleteEmployeeDailyStatusById(Long id);

	public Page<EmployeeDailyStatus> getEmployeeDailyStatusByEmployee(DataFilter dataFilter, Long id, String fromDate,
			String toDate);
}