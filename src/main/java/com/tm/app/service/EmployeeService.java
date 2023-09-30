package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.EmployeeDto;
import com.tm.app.dto.EmployeeIdNameDto;
import com.tm.app.dto.EmployeeUserDto;
import com.tm.app.entity.Employee;

public interface EmployeeService {

	public Employee saveEmployee(EmployeeDto employeeDto);

	public List<Employee> getEmployees();

	public Employee getEmployeeById(Long id);

	public Employee updateEmployee(Long id, EmployeeDto employeeDto);

	public void deleteEmployeeById(Long id);

	public List<EmployeeIdNameDto> getEmployeeIdAndName();

	public Employee getEmployeeByUserId(Long id);

	public Page<Employee> getEmployeeList(DataFilter dataFilter);

	public Employee getEmployeeUser(Long id);

	public Employee updateEmployeeUser(Long id, EmployeeUserDto employeeUserDto);

}