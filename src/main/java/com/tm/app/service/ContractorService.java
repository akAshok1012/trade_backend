package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.ContractorDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.entity.Contractor;

public interface ContractorService {

	Contractor saveContractor(ContractorDto employeeContractororDto);

	List<Contractor> getContractors();

	Contractor getContractorById(Long id);

	Contractor updateContractor(Long id, ContractorDto employeeContractororDto);

	void deleteContractorById(Long id);

	Page<Contractor> getContractorList(DataFilter dataFilter);

}
