package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.EmployeePayDto;
import com.tm.app.entity.EmployeePay;

public interface EmployeePayService {

	public List<EmployeePay> getEmployeePays();

	public EmployeePay saveEmployeePay(EmployeePayDto employeePayDto);

	public EmployeePay getEmployeePayById(Long id);

	public void deleteEmployeePayById(Long id);

	public EmployeePay updateEmployeePay(Long id, EmployeePayDto employeePayDto);

	public List<EmployeePay> getEmployeePayByEmployee(Long empId);

	public Page<EmployeePay> getEmployeePayList(DataFilter dataFilter);

}