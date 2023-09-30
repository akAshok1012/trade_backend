package com.tm.app.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.EmployeeAttendanceDto;
import com.tm.app.entity.EmployeeAttendance;
import com.tm.app.repo.EmployeeAttendanceRepo;
import com.tm.app.service.EmployeeAttendanceService;

@Service
public class EmployeeAttendanceServiceImpl implements EmployeeAttendanceService {

	@Autowired
	private EmployeeAttendanceRepo employeeAttendanceRepo;

	@Override
	public EmployeeAttendance saveEmployeeAttendance(EmployeeAttendanceDto employeeAttendanceDto) {
		EmployeeAttendance employeeAttendance = new EmployeeAttendance();
		BeanUtils.copyProperties(employeeAttendanceDto, employeeAttendance);
		employeeAttendance = employeeAttendanceRepo.save(employeeAttendance);
		return employeeAttendance;
	}

	@Override
	public List<EmployeeAttendance> getEmployeeAttendance() {
		return employeeAttendanceRepo.findAll();
	}

	@Override
	public EmployeeAttendance getEmployeeAttendanceById(Long id) {
		return employeeAttendanceRepo.findById(id).orElseThrow();
	}

	@Override
	public EmployeeAttendance updateEmployeeAttendance(Long id, EmployeeAttendanceDto employeeAttendanceDto) {
		EmployeeAttendance employeeAttendance = employeeAttendanceRepo.findById(id).orElseThrow();
		BeanUtils.copyProperties(employeeAttendanceDto, employeeAttendance);
		return employeeAttendanceRepo.save(employeeAttendance);
	}

	@Override
	public void deleteEmployeeAttendanceById(Long id) {
		employeeAttendanceRepo.deleteById(id);
	}

	@Override
	public Page<EmployeeAttendance> getEmployeeAttendanceList(DataFilter dataFilter) {
		return employeeAttendanceRepo.getEmployeeAttendanceList(dataFilter.getSearch(),
				PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
						Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}
}
