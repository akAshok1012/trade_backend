package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.EmployeeTypeDto;
import com.tm.app.entity.EmployeeType;

public interface EmployeeTypeService {

	public EmployeeType saveEmployeeType(EmployeeTypeDto employeeTypeDto);

	public List<EmployeeType> getEmployeeTypes();

	public EmployeeType getEmployeeTypeById(Long id);

	public void deleteEmployeeTypeById(Long id);

	public EmployeeType updateEmployeeType(Long id, EmployeeTypeDto employeeTypeDto);

	public Page<EmployeeType> getEmployeeTypeList(DataFilter dataFilter);

}
