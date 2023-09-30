package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.EmployeeSwipeEntryDto;
import com.tm.app.entity.EmployeeSwipeEntry;

public interface EmployeeSwipeEntryService {

	EmployeeSwipeEntry saveEmployeeSwipeEntry(EmployeeSwipeEntryDto employeeSwipeEntryDto);

	List<EmployeeSwipeEntry> getEmployeeSwipeEntry();

	EmployeeSwipeEntry getEmployeeSwipeEntryById(Long id);

	EmployeeSwipeEntry updateEmployeeSwipeEntry(Long id, EmployeeSwipeEntryDto employeeSwipeEntryDto);

	void deleteEmployeeSwipeEntryById(Long id);

	Page<EmployeeSwipeEntry> getEmployeeSwipeEntryList(DataFilter dataFilter);

}
