package com.tm.app.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.EmployeePay;
import com.tm.app.dto.EmployeePayHoursDto;
import com.tm.app.entity.EmployeePayHours;

public interface EmployeePayHoursService {

	public EmployeePayHours saveEmployeePayHours(EmployeePayHoursDto employeePayHoursDto);

	public List<EmployeePayHours> getEmployeePayHours();

	public EmployeePayHours getEmployeePayHoursById(Long id);

	public EmployeePayHours updateEmployeePayHours(Long id, EmployeePayHoursDto employeePayHoursDto);

	public void deleteEmployeePayHours(Long id);

	public Page<EmployeePayHours> getEmployeePayHoursList(DataFilter dataFilter);

	public EmployeePay getEmployeePayHoursFilter(Long id, Date fromDate, Date toDate);

}
