package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.ContractDetailsDto;
import com.tm.app.entity.ContractDetails;
import com.tm.app.enums.ContractStatus;

public interface ContractDetailsService {

	ContractDetails saveEmployeeContract(ContractDetailsDto employeeContratDto);

	List<ContractDetails> getEmployeeContracts();

	ContractDetails getEmployeeContractById(Long id);

	ContractDetails updateEmployeeContract(Long id, ContractDetailsDto employeeContratDto);

	void deleteEmployeeContractById(Long id);

	Page<ContractDetails> getEmployeeContractList(DataFilter dataFilter, ContractStatus contractStatus);

	void updateContractStatusJob();

}
