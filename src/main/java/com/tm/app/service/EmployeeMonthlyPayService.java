package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.entity.EmployeeMonthlyPay;

public interface EmployeeMonthlyPayService {

	List<EmployeeMonthlyPay> getEmployeeMonthlyPay();

	Page<EmployeeMonthlyPay> getEmployeeMonthlyPayList(DataFilter dataFilter);

}
