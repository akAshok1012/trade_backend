package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.EmployeeContractorDto;
import com.tm.app.entity.EmployeeContractor;

public interface EmployeeContractorService {

	EmployeeContractor saveEmployeeContractor(EmployeeContractorDto employeeContractorDto);

	List<EmployeeContractor> getEmployeeContractors();

	EmployeeContractor getEmployeeContractorById(Long id);

	EmployeeContractor updateEmployeeContractor(Long id, EmployeeContractorDto employeeContractorDto);

	void deleteEmployeeContractorById(Long id);

	Page<EmployeeContractor> getEmployeeContractorList(DataFilter dataFilter);
}