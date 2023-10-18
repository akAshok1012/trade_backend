package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.EmployeeWeeklyWagesDto;
import com.tm.app.entity.EmployeeWeeklyWages;

public interface EmployeeWeeklyWagesService {

	List<EmployeeWeeklyWages> getEmployeeWeeklyWages();

	EmployeeWeeklyWages getEmployeePayHoursById(Long id);

	EmployeeWeeklyWages saveEmployeeWeeklyWages(EmployeeWeeklyWagesDto employeeWeeklyWagesDto);

	EmployeeWeeklyWages updateEmployeeWeeklyWages(Long id, EmployeeWeeklyWagesDto employeeWeeklyWagesDto);

	void deleteEmployeeWeeklyWages(Long id);

	Page<EmployeeWeeklyWages> getEmployeeWeeklyWagesList(DataFilter dataFilter);

}
