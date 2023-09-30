package com.tm.app.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.ContractEmployeeDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.entity.ContractEmployee;
import com.tm.app.repo.ContractEmployeeRepo;
import com.tm.app.service.ContractEmployeeService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class ContractEmployeeServiceImpl implements ContractEmployeeService {

	@Autowired
	private ContractEmployeeRepo contractEmployeeRepo;

	@Override
	public ContractEmployee saveContractEmployee(ContractEmployeeDto contractEmployeeDto) {
		log.info("[CONTRACT_EMPLOYEE] saveContractEmployee starts");
		ContractEmployee contractorEmployee = new ContractEmployee();
		try {
			if (Objects.nonNull(contractEmployeeDto.getPhoneNumber())
					&& contractEmployeeRepo.existsByPhoneNumber(contractEmployeeDto.getPhoneNumber())) {
				throw new RuntimeException("PhoneNumber  already exists");
			}
			if (Objects.nonNull(contractEmployeeDto.getAadhaarNumber())
					&& contractEmployeeRepo.existsByAadhaarNumber(contractEmployeeDto.getAadhaarNumber())) {
				throw new RuntimeException("AadhaarNumber  already exists");
			}
			BeanUtils.copyProperties(contractEmployeeDto, contractorEmployee);
			return contractEmployeeRepo.save(contractorEmployee);
		} catch (Exception e) {
			log.error("[CONTRACT_EMPLOYEE] saveContractEmployee failed", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<ContractEmployee> getContractEmployees() {
		return contractEmployeeRepo.findAll();
	}

	@Override
	public ContractEmployee getContractEmployeeById(Long id) {
		return contractEmployeeRepo.findById(id).orElseThrow();
	}

	@Override
	public ContractEmployee updateContractEmployee(Long id, ContractEmployeeDto contractEmployeeDto) {
		log.info("[CONTRACT_EMPLOYEE] updateContractEmployee starts");
		ContractEmployee contractEmployee = contractEmployeeRepo.findById(id).orElseThrow();
		try {
			if (Objects.nonNull(contractEmployeeDto.getPhoneNumber())
					&& contractEmployeeRepo.existsByPhoneNumber(contractEmployeeDto.getPhoneNumber())
					&& !contractEmployee.getPhoneNumber().equals(contractEmployeeDto.getPhoneNumber())) {
				throw new RuntimeException("PhoneNumber  already exists");
			}
			if (Objects.nonNull(contractEmployeeDto.getAadhaarNumber())
					&& contractEmployeeRepo.existsByAadhaarNumber(contractEmployeeDto.getAadhaarNumber())
					&& !contractEmployee.getAadhaarNumber().equals(contractEmployeeDto.getAadhaarNumber())) {
				throw new RuntimeException("AadhaarNumber  already exists");
			}
			contractEmployee.setName(contractEmployeeDto.getName());
			contractEmployee.setContractor(contractEmployeeDto.getContractor());
			contractEmployee.setAadhaarNumber(contractEmployeeDto.getAadhaarNumber());
			contractEmployee.setPhoneNumber(contractEmployeeDto.getPhoneNumber());
			contractEmployee.setAddress(contractEmployeeDto.getAddress());
			contractEmployee.setNotes(contractEmployeeDto.getNotes());
			contractEmployee.setUpdatedBy(contractEmployeeDto.getUpdatedBy());
			return contractEmployeeRepo.save(contractEmployee);
		} catch (Exception e) {
			log.error("[CONTRACT_EMPLOYEE] updateContractEmployee failed", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void deleteContractEmployeeById(Long id) {
		contractEmployeeRepo.deleteById(id);
	}

	@Override
	public Page<ContractEmployee> getContractEmployeeList(DataFilter dataFilter) {
		return contractEmployeeRepo.getContractEmployeeList(dataFilter.getSearch(), PageRequest.of(dataFilter.getPage(),
				dataFilter.getSize(), Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}

	@Override
	public List<ContractEmployee> getContractEmployeesNotInContract() {
		List<Long> contractEmployeesNotInContract = contractEmployeeRepo.getContractEmployeesNotInContract();
		return contractEmployeeRepo.findAllById(contractEmployeesNotInContract);
	}

	@Override
	public List<ContractEmployee> getContractEmployeesByContractor(Long id) {
		return contractEmployeeRepo.getContractEmployeesByContractor(id);
	}
}