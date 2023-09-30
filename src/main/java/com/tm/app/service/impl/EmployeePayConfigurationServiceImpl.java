package com.tm.app.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.EmployeePayConfigurationDto;
import com.tm.app.entity.EmployeePayConfiguration;
import com.tm.app.repo.EmployeePayConfigurationRepo;
import com.tm.app.service.EmployeePayConfigurationService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class EmployeePayConfigurationServiceImpl implements EmployeePayConfigurationService {

	@Autowired
	private EmployeePayConfigurationRepo employeePayConfigurationRepo;

	@Override
	public EmployeePayConfiguration saveEmployeePayConfiguration(
			EmployeePayConfigurationDto employeePayConfigurationDto) {
		log.info("[EmployeePayServiceImpl] saveEmployeePay starts...");

		EmployeePayConfiguration employeePayConfigurations = new EmployeePayConfiguration();
		BeanUtils.copyProperties(employeePayConfigurationDto, employeePayConfigurations);
		employeePayConfigurations = employeePayConfigurationRepo.save(employeePayConfigurations);

		log.info("[EmployeePayServiceImpl] saveEmployeePay ends...");
		return employeePayConfigurations;
	}

	@Override
	public List<EmployeePayConfiguration> getEmployeePayConfigurations() {
		return  employeePayConfigurationRepo.findAll();
	}

	@Override
	public EmployeePayConfiguration getEmployeePayConfigurationById(Long id) {
		log.info("[EmployeePayConfigurationServiceImpl] getEmployeePayConfigurationById starts...");
		return employeePayConfigurationRepo.findById(id).orElseThrow();
	}

	@Override
	public EmployeePayConfiguration updateEmployeePayConfiguration(Long id,
			EmployeePayConfigurationDto employeePayConfigurationDto) {
		log.info("[EmployeePayConfigurationServiceImpl] updateEmployeePayConfiguration starts...");
		EmployeePayConfiguration employeePayConfigurations = employeePayConfigurationRepo.findById(id).orElseThrow();
		try {
			BeanUtils.copyProperties(employeePayConfigurationDto, employeePayConfigurations);
			employeePayConfigurations = employeePayConfigurationRepo.save(employeePayConfigurations);
		} catch (Exception e) {
			log.error("[EMPLOYEE] updating employee pay Configuration failed", e);
			throw new RuntimeException("Updating employee pay Configuration failed");
		}
		log.info("[EmployeePayConfigurationServiceImpl] updateEmployeePayConfiguration ends...");
		return employeePayConfigurations;
	}

	@Override
	public void deleteEmployeePayConfigurationById(Long id) {
		log.info("[EmployeePayConfigurationServiceImpl] deleteEmployeePayConfigurationById starts...");
		try {
			employeePayConfigurationRepo.deleteById(id);
		} catch (Exception e) {
			log.error("[EMPLOYEE] deleting employee pay Configuration failed", e);
			throw new RuntimeException("Deleting employee pay Configuration failed");
		}
		log.info("[EmployeePayConfigurationServiceImpl] deleteEmployeePayConfigurationById ends...");

	}

	@Override
	public Page<EmployeePayConfiguration> getEmployeePayConfigurationList(DataFilter dataFilter) {
		return employeePayConfigurationRepo.findAll(PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
				Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}
	
	@Override
	public EmployeePayConfiguration getEmployeePayConfiguration() {
		return employeePayConfigurationRepo.findAll().get(0);
	}

}
