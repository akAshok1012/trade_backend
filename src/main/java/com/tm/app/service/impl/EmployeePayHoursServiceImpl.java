package com.tm.app.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.EmployeePay;
import com.tm.app.dto.EmployeePayHoursDto;
import com.tm.app.entity.EmployeePayHours;
import com.tm.app.repo.EmployeePayHoursRepo;
import com.tm.app.service.EmployeePayHoursService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class EmployeePayHoursServiceImpl implements EmployeePayHoursService {

	@Autowired
	private EmployeePayHoursRepo employeePayHoursRepo;

	@Override
	public EmployeePayHours saveEmployeePayHours(EmployeePayHoursDto employeePayHoursDto) {
		log.info("[EmployeePayHoursServiceImpl] Saved EmployeePayHours Starts");
		EmployeePayHours employeePayHours = new EmployeePayHours();
		try {
			BeanUtils.copyProperties(employeePayHoursDto, employeePayHours);
			employeePayHours = employeePayHoursRepo.save(employeePayHours);
		} catch (Exception e) {
			log.error("[EmployeePayHoursServiceImpl] Saved EmployeePayHours Failed");
			throw new RuntimeException(e.getMessage());
		}
		return employeePayHours;
	}

	@Override
	public List<EmployeePayHours> getEmployeePayHours() {
		log.info("[EmployeePayHoursServiceImpl] Get EmployeePayHours Starts");
		try {
			return employeePayHoursRepo.findAll();
		} catch (Exception e) {
			log.error("[EmployeePayHoursServiceImpl] Get EmployeePayHours Failed");
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public EmployeePayHours getEmployeePayHoursById(Long id) {
		log.info("[EmployeePayHoursServiceImpl] GetById EmployeePayHours Starts");
		try {
			return employeePayHoursRepo.findById(id).orElseThrow();
		} catch (Exception e) {
			log.error("[EmployeePayHoursServiceImpl] GetById EmployeePayHours Failed");
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public EmployeePayHours updateEmployeePayHours(Long id, EmployeePayHoursDto employeePayHoursDto) {
		log.info("[EmployeePayHoursServiceImpl] Updated EmployeePayHours Starts");
		EmployeePayHours employeePayHours = employeePayHoursRepo.findById(id).orElseThrow();
		try {
			BeanUtils.copyProperties(employeePayHoursDto, employeePayHours);
			employeePayHours = employeePayHoursRepo.save(employeePayHours);
		} catch (Exception e) {
			log.error("[EmployeePayHoursServiceImpl] Updated EmployeePayHours Failed");
			throw new RuntimeException(e.getMessage());
		}
		return employeePayHours;
	}

	@Override
	public void deleteEmployeePayHours(Long id) {
		log.info("[EmployeePayHoursServiceImpl] Deleted EmployeePayHours Starts");
		try {
			employeePayHoursRepo.deleteById(id);
		} catch (Exception e) {
			log.error("[EmployeePayHoursServiceImpl] Deleted EmployeePayHours Failed", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Page<EmployeePayHours> getEmployeePayHoursList(DataFilter dataFilter) {
		log.info("[EmployeePayHoursServiceImpl] Get EmployeePayHoursList Starts");
		try {
			return employeePayHoursRepo.findByEmployeeNameLikeIgnoreCase(dataFilter.getSearch(),
					PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
							Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
		} catch (Exception e) {
			log.error("[EmployeePayHoursServiceImpl] Get EmployeePayHoursList Failed");
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public EmployeePay getEmployeePayHoursFilter(Long id, Date fromDate, Date toDate) {
		EmployeePay employeePay = new EmployeePay();
		List<Object[]> employeePayobj = employeePayHoursRepo.getEmployeePayHoursFilter(id, fromDate, toDate);
		employeePayobj.stream().forEach(r -> {
			employeePay.setTotalPay(r[0]);
			employeePay.setTotalHoursWorked(r[1]);
		});
		return employeePay;
	}

}
