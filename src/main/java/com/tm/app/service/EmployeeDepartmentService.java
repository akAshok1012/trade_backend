package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.EmployeeDepartmentDto;
import com.tm.app.dto.EmployeeDepartmentIdNameDto;
import com.tm.app.entity.EmployeeDepartment;

public interface EmployeeDepartmentService {

	public EmployeeDepartment saveEmployeeDepartment(EmployeeDepartmentDto employeeDepartmentDto);

	public List<EmployeeDepartment> getEmployeeDepartment();

	public EmployeeDepartment getEmployeeDepartmentById(Long id);

	public EmployeeDepartment updateEmployeeDepartment(Long id, EmployeeDepartmentDto employeeDepartmentDto);

	public void deleteDepartmentById(Long id);

	public List<EmployeeDepartmentIdNameDto> getEmployeeDepartmentName();

	public Page<EmployeeDepartment> getEmployeeDepartmentList(DataFilter dataFilter);

}