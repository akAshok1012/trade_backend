package com.tm.app.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.EmployeeContractorDto;
import com.tm.app.entity.EmployeeContractor;
import com.tm.app.repo.EmployeeContractorRepo;
import com.tm.app.service.EmployeeContractorService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeContractorServiceImpl implements EmployeeContractorService {

	@Autowired
	private EmployeeContractorRepo employeeContractorRepo;

	@Override
	@Transactional
	public EmployeeContractor saveEmployeeContractor(EmployeeContractorDto employeeContractorDto) {
		EmployeeContractor contractor = new EmployeeContractor();
		try {
			BeanUtils.copyProperties(employeeContractorDto, contractor);
			contractor= employeeContractorRepo.save(contractor);
		} catch (Exception e) {
			log.error("[EMPLOYEE] Adding employee contractor failed",e);
			throw new RuntimeException("Adding employee contractor failed");
		}
		return contractor;
	}

	@Override
	public List<EmployeeContractor> getEmployeeContractors() {
		return employeeContractorRepo.findAll();
	}

	@Override
	public EmployeeContractor getEmployeeContractorById(Long id) {
		return employeeContractorRepo.findById(id).orElseThrow();
	}

	@Override
	@Transactional
	public EmployeeContractor updateEmployeeContractor(Long id, EmployeeContractorDto employeeContractorDto) {
		EmployeeContractor contractor = employeeContractorRepo.findById(id).orElseThrow();
		try {
			contractor.setFirstName(employeeContractorDto.getFirstName());
			contractor.setLastName(employeeContractorDto.getLastName());
			contractor.setHireDate(employeeContractorDto.getHireDate());
			contractor.setEndDate(employeeContractorDto.getEndDate());
			contractor.setDailyRate(employeeContractorDto.getDailyRate());
			contractor.setTitle(employeeContractorDto.getTitle());
			contractor.setDepartment(employeeContractorDto.getDepartment());
			contractor.setUpdatedBy(employeeContractorDto.getUpdatedBy());
			contractor = employeeContractorRepo.save(contractor);
			
		} catch (Exception e) {
			log.error("[EMPLOYEE] Updating employee contractor failed",e);
			throw new RuntimeException("Adding employee contractor failed");
		}
		return contractor;
	}

	@Override
	@Transactional
	public void deleteEmployeeContractorById(Long id) {
		try {
			employeeContractorRepo.deleteById(id);
		} catch (Exception e) {
			log.error("[EMPLOYEE] Deleting employee contractor failed",e);
			throw new RuntimeException("Deleting employee contractor failed");
		}
	}

	@Override
	public Page<EmployeeContractor> getEmployeeContractorList(DataFilter dataFilter) {
		return employeeContractorRepo.findByFirstNameOrDepartment(dataFilter.getSearch(),
				PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
						Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}
}