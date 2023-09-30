package com.tm.app.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.EmployeeDailyStatusDto;
import com.tm.app.entity.Employee;
import com.tm.app.entity.EmployeeDailyStatus;
import com.tm.app.entity.User;
import com.tm.app.repo.EmployeeDailyStatusRepo;
import com.tm.app.repo.EmployeeRepo;
import com.tm.app.repo.UserRepository;
import com.tm.app.service.EmployeeDailyStatusService;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class EmployeeDailyStatusServiceImpl implements EmployeeDailyStatusService {

	@Autowired
	private EmployeeDailyStatusRepo employeeDailyStatusRepo;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmployeeRepo employeeRepo;

	@Override
	public EmployeeDailyStatus saveEmployeeDailyStatus(EmployeeDailyStatusDto employeeDailyStatusDto) {
		log.info("[EmployeeDailyStatusServiceImpl] saveEmployeeDailyStatus starts ");
		
		EmployeeDailyStatus empDailyStatus = new EmployeeDailyStatus();
		try {
			BeanUtils.copyProperties(employeeDailyStatusDto, empDailyStatus);
			empDailyStatus = employeeDailyStatusRepo.save(empDailyStatus);
		} catch (Exception e) {
			log.error("[EmployeeDailyStatusServiceImpl] save daily status failed", e);
			throw new RuntimeException("Save employee daily status failed");
		}
		
		log.info("[EmployeeDailyStatusServiceImpl] saveEmployeeDailyStatus ends ");
		return empDailyStatus;
	}

	@Override
	public List<EmployeeDailyStatus> getEmployeeDailyStatus() {
		return employeeDailyStatusRepo.findAll();
	}

	@Override
	public EmployeeDailyStatus getEmployeeDailyStatusById(Long id) {
		return employeeDailyStatusRepo.findById(id).orElseThrow();
	}

	@Override
	public EmployeeDailyStatus updateEmployeeDailyStatus(Long id, EmployeeDailyStatusDto employeeDailyStatusDto) {
		EmployeeDailyStatus empDailyStatus = employeeDailyStatusRepo.findById(id).orElseThrow();
		try {
			empDailyStatus.setEmployee(employeeDailyStatusDto.getEmployee());
			empDailyStatus.setNotes(employeeDailyStatusDto.getNotes());
			empDailyStatus.setDate(employeeDailyStatusDto.getDate());
			empDailyStatus.setUpdatedBy(employeeDailyStatusDto.getUpdatedBy());
			empDailyStatus.setDailyStatus(employeeDailyStatusDto.getDailyStatus());
			empDailyStatus = employeeDailyStatusRepo.save(empDailyStatus);
		} catch (Exception e) {
			log.error("[EMPLOYEE] update daily status failed", e);
			throw new RuntimeException(
					"Update employee " + empDailyStatus.getEmployee().getName() + " daily status failed");
		}
		return empDailyStatus;
	}

	@Override
	public void deleteEmployeeDailyStatusById(Long id) {
		try {
			employeeDailyStatusRepo.deleteById(id);
		} catch (Exception e) {
			log.error("[EMPLOYEE] delete daily status failed", e);
			throw new RuntimeException("Deleting employee daily status failed");
		}
	}

	@Override
	public Page<EmployeeDailyStatus> getEmployeeDailyStatusByEmployee(DataFilter dataFilter, Long id, String fromDate,
			String toDate) {
		log.info("[EmployeeDailyStatusServiceImpl] getEmployeeDailyStatusByEmployee starts ");
		log.info("[FROM_DATE] ", fromDate);
		log.info("[TO_DATE] ", toDate);
		if (Objects.nonNull(id)) {
			User user = userRepository.findById(id).orElseThrow();
			Employee employee = employeeRepo.findById(user.getUserId()).orElseThrow();
			if (StringUtils.isNotEmpty(fromDate) && StringUtils.isNotEmpty(toDate)) {
				return employeeDailyStatusRepo.getEmployeeDailyStatusByEmployee(employee, fromDate, toDate,
						PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
								Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
			} else {
				return employeeDailyStatusRepo.findByEmployee(employee, PageRequest.of(dataFilter.getPage(),
						dataFilter.getSize(), Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
			}
		} else {
			if (StringUtils.isNotEmpty(fromDate) && StringUtils.isNotEmpty(toDate)) {
				return employeeDailyStatusRepo
						.getAllEmployeeDailyStatusByFromAndToDate(dataFilter.getSearch(),
								PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())),
								fromDate, toDate);
			} else {
				return employeeDailyStatusRepo.getEmployeeDailyStatusBySearchString(dataFilter.getSearch(),
						PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
								Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
			}
		}
	}
}