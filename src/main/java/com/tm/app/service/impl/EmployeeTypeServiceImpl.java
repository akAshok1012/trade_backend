package com.tm.app.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.EmployeeTypeDto;
import com.tm.app.entity.EmployeeType;
import com.tm.app.repo.EmployeeTypeRepo;
import com.tm.app.service.EmployeeTypeService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeTypeServiceImpl implements EmployeeTypeService {

	@Autowired
	private EmployeeTypeRepo employeeTypeRepo;

	@Override
	@Transactional
	public EmployeeType saveEmployeeType(EmployeeTypeDto employeeTypeDto) {
		EmployeeType employeeType = new EmployeeType();
		try {
			BeanUtils.copyProperties(employeeTypeDto, employeeType);
			employeeType = employeeTypeRepo.save(employeeType);
		} catch (BeansException e) {
			log.error("[EMPLOYEE] adding employee type failed", e);
			throw new RuntimeException("Adding employee type failed");
		}
		return employeeType;
	}

	@Override
	public List<EmployeeType> getEmployeeTypes() {
		return employeeTypeRepo.findAll();
	}

	@Override
	public EmployeeType getEmployeeTypeById(Long id) {
		return employeeTypeRepo.findById(id).orElseThrow();
	}

	@Override
	@Transactional
	public EmployeeType updateEmployeeType(Long id, EmployeeTypeDto employeeTypeDto) {
		EmployeeType employeeTypes = employeeTypeRepo.findById(id).orElseThrow();
		try {
			employeeTypes.setEmployeeType(employeeTypeDto.getEmployeeType());
			employeeTypes.setDescription(employeeTypeDto.getDescription());
			employeeTypes.setUpdatedBy(employeeTypeDto.getUpdatedBy());
			employeeTypes = employeeTypeRepo.save(employeeTypes);
		} catch (Exception e) {
			log.error("[EMPLOYEE] updating employee type failed", e);
			throw new RuntimeException("Updating employee type failed");
		}
		return employeeTypes;
	}

	@Override
	@Transactional
	public void deleteEmployeeTypeById(Long id) {
		try {
			employeeTypeRepo.deleteById(id);
		} catch (Exception e) {
			log.error("[EMPLOYEE] deleting employee type failed", e);
			throw new RuntimeException("Deleting employee type failed");
		}
	}

	@Override
	public Page<EmployeeType> getEmployeeTypeList(DataFilter dataFilter) {
		return employeeTypeRepo.findByEmployeeTypeLikeIgnoreCase(dataFilter.getSearch(),
				PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
						Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}
}