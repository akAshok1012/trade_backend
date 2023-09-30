package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.EmployeePayConfigurationDto;
import com.tm.app.entity.EmployeePayConfiguration;

public interface EmployeePayConfigurationService {

	EmployeePayConfiguration saveEmployeePayConfiguration(EmployeePayConfigurationDto employeePayConfigurationDto);

	List<EmployeePayConfiguration> getEmployeePayConfigurations();

	EmployeePayConfiguration getEmployeePayConfigurationById(Long id);

	EmployeePayConfiguration updateEmployeePayConfiguration(Long id,EmployeePayConfigurationDto employeePayConfigurationDto);

	void deleteEmployeePayConfigurationById(Long id);

	Page<EmployeePayConfiguration> getEmployeePayConfigurationList(DataFilter dataFilter);

	EmployeePayConfiguration getEmployeePayConfiguration();

}
