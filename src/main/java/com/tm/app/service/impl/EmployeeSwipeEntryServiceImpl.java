package com.tm.app.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.EmployeeSwipeEntryDto;
import com.tm.app.entity.EmployeeSwipeEntry;
import com.tm.app.repo.EmployeeSwipeEntryRepo;
import com.tm.app.service.EmployeeSwipeEntryService;

@Service
public class EmployeeSwipeEntryServiceImpl implements EmployeeSwipeEntryService {

	@Autowired
	private EmployeeSwipeEntryRepo employeeSwipeEntryRepo;

	@Override
	public EmployeeSwipeEntry saveEmployeeSwipeEntry(EmployeeSwipeEntryDto employeeSwipeEntryDto) {
		EmployeeSwipeEntry employeeSwipeEntry = new EmployeeSwipeEntry();
		BeanUtils.copyProperties(employeeSwipeEntryDto, employeeSwipeEntry);
		employeeSwipeEntry = employeeSwipeEntryRepo.save(employeeSwipeEntry);
		return employeeSwipeEntry;
	}

	@Override
	public List<EmployeeSwipeEntry> getEmployeeSwipeEntry() {
		return employeeSwipeEntryRepo.findAll();
	}

	@Override
	public EmployeeSwipeEntry getEmployeeSwipeEntryById(Long id) {
		return employeeSwipeEntryRepo.findById(id).orElseThrow();
	}

	@Override
	public EmployeeSwipeEntry updateEmployeeSwipeEntry(Long id, EmployeeSwipeEntryDto employeeSwipeEntryDto) {
		EmployeeSwipeEntry employeeSwipeEntry = employeeSwipeEntryRepo.findById(id).orElseThrow();
		employeeSwipeEntry.setSwipeType(employeeSwipeEntryDto.getSwipeType());
		employeeSwipeEntry.setRemarks(employeeSwipeEntryDto.getRemarks());
		employeeSwipeEntry.setUpdatedBy(employeeSwipeEntryDto.getUpdatedBy());
		return employeeSwipeEntryRepo.save(employeeSwipeEntry);
	}

	@Override
	public void deleteEmployeeSwipeEntryById(Long id) {
		employeeSwipeEntryRepo.deleteById(id);
	}

	@Override
	public Page<EmployeeSwipeEntry> getEmployeeSwipeEntryList(DataFilter dataFilter) {
		return employeeSwipeEntryRepo.getEmployeeSwipeEntryList(dataFilter.getSearch(),
				PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
						Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}

}
