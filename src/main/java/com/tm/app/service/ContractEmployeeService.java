package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.ContractEmployeeDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.entity.ContractEmployee;

public interface ContractEmployeeService {

	ContractEmployee saveContractEmployee(ContractEmployeeDto contractEmployeeDto);

	List<ContractEmployee> getContractEmployees();

	ContractEmployee getContractEmployeeById(Long id);

	ContractEmployee updateContractEmployee(Long id, ContractEmployeeDto contractEmployeeDto);

	void deleteContractEmployeeById(Long id);

	Page<ContractEmployee> getContractEmployeeList(DataFilter dataFilter);

	List<ContractEmployee> getContractEmployeesNotInContract();

	List<ContractEmployee> getContractEmployeesByContractor(Long id);

}