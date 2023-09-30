package com.tm.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.entity.EmployeeMonthlyPay;
import com.tm.app.repo.EmployeeMonthlyPayRepo;
import com.tm.app.service.EmployeeMonthlyPayService;

@Service
public class EmployeeMonthlyPayServiceImpl implements EmployeeMonthlyPayService {

	@Autowired
	private EmployeeMonthlyPayRepo employeeMonthlyPayRepo;

	@Override
	public List<EmployeeMonthlyPay> getEmployeeMonthlyPay() {
		return employeeMonthlyPayRepo.findAll();
	}

	@Override
	public Page<EmployeeMonthlyPay> getEmployeeMonthlyPayList(DataFilter dataFilter) {
		return employeeMonthlyPayRepo.getEmployeeMonthlyPayList(dataFilter.getSearch(),PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
				Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}
	
	

}
