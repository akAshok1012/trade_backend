package com.tm.app.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.EmployeeWeeklyWagesDto;
import com.tm.app.entity.EmployeeWeeklyWages;
import com.tm.app.repo.EmployeeWeeklyWagesRepo;
import com.tm.app.service.EmployeeWeeklyWagesService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class EmployeeWeeklyWagesServiceImpl implements EmployeeWeeklyWagesService {

	@Autowired
	private EmployeeWeeklyWagesRepo employeeWeeklyWagesRepo;
	
	@Override
	public List<EmployeeWeeklyWages> getEmployeeWeeklyWages() {
		log.info("[EmployeeWeeklyWagesServiceImpl] Get EmployeeWeeklyWages Starts");
		try {
			return employeeWeeklyWagesRepo.findAll();
		} catch (Exception e) {
			log.error("[EmployeeWeeklyWagesServiceImpl] Get EmployeeWeeklyWages Failed");
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public EmployeeWeeklyWages getEmployeePayHoursById(Long id) {
		log.info("[EmployeeWeeklyWagesServiceImpl] GetById EmployeeWeeklyWages Starts");
		try {
			return employeeWeeklyWagesRepo.findById(id).orElseThrow();
		} catch (Exception e) {
			log.error("[EmployeeWeeklyWagesServiceImpl] GetById EmployeeWeeklyWages Failed");
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public EmployeeWeeklyWages saveEmployeeWeeklyWages(EmployeeWeeklyWagesDto employeeWeeklyWagesDto) {
		log.info("[EmployeeWeeklyWagesServiceImpl] Saved EmployeeWeeklyWages Starts");
		EmployeeWeeklyWages employeeWeeklyWages = new EmployeeWeeklyWages();
		try {
			BeanUtils.copyProperties(employeeWeeklyWagesDto, employeeWeeklyWages);
			employeeWeeklyWages = employeeWeeklyWagesRepo.save(employeeWeeklyWages);
		} catch (Exception e) {
			log.error("[EmployeeWeeklyWagesServiceImpl] Saved  Failed");
			throw new RuntimeException(e.getMessage());
		}
		return employeeWeeklyWages;
	}


	@Override
	public EmployeeWeeklyWages updateEmployeeWeeklyWages(Long id, EmployeeWeeklyWagesDto employeeWeeklyWagesDto) {
		log.info("[EmployeeWeeklyWagesServiceImpl] Updated EmployeeWeeklyWages Starts");
		EmployeeWeeklyWages employeeWeeklyWages = employeeWeeklyWagesRepo.findById(id).orElseThrow();
		try {
			BeanUtils.copyProperties(employeeWeeklyWagesDto, employeeWeeklyWages);
			employeeWeeklyWages = employeeWeeklyWagesRepo.save(employeeWeeklyWages);
		} catch (Exception e) {
			log.error("[EmployeeWeeklyWagesServiceImpl] Updated EmployeeWeeklyWages Failed");
			throw new RuntimeException(e.getMessage());
		}
		return employeeWeeklyWages;
	}

	@Override
	public void deleteEmployeeWeeklyWages(Long id) {
		log.info("[EmployeeWeeklyWagesServiceImpl] Deleted EmployeeWeeklyWages Starts");
		try {
			employeeWeeklyWagesRepo.deleteById(id);
		} catch (Exception e) {
			log.error("[EmployeeWeeklyWagesServiceImpl] Deleted EmployeeWeeklyWages Failed", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Page<EmployeeWeeklyWages> getEmployeeWeeklyWagesList(DataFilter dataFilter) {
		log.info("[EmployeeWeeklyWagesServiceImpl] Get EmployeeWeeklyWages Starts");
		try {
			return employeeWeeklyWagesRepo.findByEmployeeNameLikeIgnoreCase(dataFilter.getSearch(),
					PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
							Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
		} catch (Exception e) {
			log.error("[EmployeeWeeklyWagesServiceImpl] Get EmployeeWeeklyWages Failed");
			throw new RuntimeException(e.getMessage());
		}
	}

}
