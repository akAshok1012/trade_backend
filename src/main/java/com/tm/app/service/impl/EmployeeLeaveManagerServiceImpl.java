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
import com.tm.app.dto.EmployeeLeaveManagerDto;
import com.tm.app.dto.EmployeeLeaveStatusDto;
import com.tm.app.entity.Employee;
import com.tm.app.entity.EmployeeLeaveManager;
import com.tm.app.entity.User;
import com.tm.app.enums.EmployeeLeaveStatus;
import com.tm.app.enums.LeaveType;
import com.tm.app.repo.EmployeeLeaveManagerRepo;
import com.tm.app.repo.EmployeeRepo;
import com.tm.app.repo.UserRepository;
import com.tm.app.service.EmployeeLeaveManagerService;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class EmployeeLeaveManagerServiceImpl implements EmployeeLeaveManagerService {

	@Autowired
	private EmployeeLeaveManagerRepo employeeLeaveManagerRepo;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmployeeRepo employeeRepo;

	@Override
	public EmployeeLeaveManager applyEmployeeLeave(EmployeeLeaveManagerDto employeeLeaveManagerDto) {
		EmployeeLeaveManager empLeaveManager = new EmployeeLeaveManager();
		try {
			employeeLeaveManagerDto.setEmployeeLeaveStatus(EmployeeLeaveStatus.PENDING);
			if (employeeLeaveManagerRepo.existsByEmployeeAndStartDateAndEndDate(employeeLeaveManagerDto.getEmployee(),
					employeeLeaveManagerDto.getStartDate(), employeeLeaveManagerDto.getEndDate())) {
				throw new RuntimeException("Employee leave already exists");
			}
			if(employeeLeaveManagerRepo.getCompensationDateBetweenStartAndEndDates(employeeLeaveManagerDto.getEmployee().getId(),
							employeeLeaveManagerDto.getStartDate(),employeeLeaveManagerDto.getEndDate()).isPresent()) {
				throw new RuntimeException("Compensation is present");
			}
			if (Objects.nonNull(employeeLeaveManagerDto.getCompensationDate()) && (employeeLeaveManagerRepo
					.getCompensationDateBetweenStartAndEndDate(employeeLeaveManagerDto.getEmployee().getId(),
							employeeLeaveManagerDto.getCompensationDate())
					.isPresent()
					|| employeeLeaveManagerRepo.findByEmployeeAndCompensationDate(employeeLeaveManagerDto.getEmployee(),
							employeeLeaveManagerDto.getCompensationDate()).isPresent())) {
				throw new RuntimeException("Compensation leave already exists");
			}
			BeanUtils.copyProperties(employeeLeaveManagerDto, empLeaveManager);
			empLeaveManager = employeeLeaveManagerRepo.save(empLeaveManager);
		} catch (Exception e) {
			log.error("[EmployeeLeaveManagerServiceImpl] leave request failed", e);
			throw new RuntimeException(e.getMessage());
		}
		return empLeaveManager;
	}

	@Override
	public Page<EmployeeLeaveManager> getEmployeeLeaves(DataFilter dataFilter, Long id,
			EmployeeLeaveStatus employeeLeaveStatus, String fromDate, String toDate) {
		log.info("[EmployeeLeaveManagerServiceImpl] getEmployeeLeaves starts ");
		log.info("[FROM_DATE] ", fromDate);
		log.info("[TO_DATE] ", toDate);

		if (Objects.nonNull(id)) {
			User user = userRepository.findById(id).orElseThrow();
			Employee employee = employeeRepo.findById(user.getUserId()).orElseThrow();
			if (StringUtils.isNotEmpty(fromDate) && StringUtils.isNotEmpty(toDate)) {
				return employeeLeaveManagerRepo
						.getEmployeeLeavesWithDateFilter(employee, employeeLeaveStatus,
								PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
										Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())),
								fromDate, toDate);
			} else {
				return employeeLeaveManagerRepo.getEmployeeLeaves(employee, employeeLeaveStatus,
						PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
								Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
			}
		} else {
			if (StringUtils.isNotEmpty(fromDate) && StringUtils.isNotEmpty(toDate)) {
				return employeeLeaveManagerRepo.getEmployeeLeaveSearch(
						PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
								Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())),
						fromDate, toDate, dataFilter.getSearch(), employeeLeaveStatus);
			} else {
				return employeeLeaveManagerRepo
						.getEmployeeLeaveStatusWithSearch(dataFilter.getSearch(),
								PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
										Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())),
								employeeLeaveStatus);
			}
		}
	}

	@Override
	public EmployeeLeaveManager getEmployeeLeaveById(Long id) {
		return employeeLeaveManagerRepo.findById(id).orElseThrow();
	}

	@Override
	public EmployeeLeaveManager approveEmployeeLeave(Long id, EmployeeLeaveManagerDto employeeLeaveManagerDto) {
		try {
			EmployeeLeaveManager empLeaveManager = employeeLeaveManagerRepo.findById(id).orElseThrow();
			BeanUtils.copyProperties(employeeLeaveManagerDto, empLeaveManager);
			empLeaveManager.setIsNotificationEnabled(false);
			return employeeLeaveManagerRepo.save(empLeaveManager);
		} catch (Exception e) {
			log.error("[EMPLOYEE] leave approval failed", e);
			throw new RuntimeException("Employee leave approval failed");
		}
	}

	@Override
	public void deleteEmployeeLeaveById(Long id) {
		try {
			employeeLeaveManagerRepo.deleteById(id);
		} catch (Exception e) {
			log.error("[EMPLOYEE] deleting leave request failed", e);
			throw new RuntimeException("Deleting leave request failed");
		}
	}

	@Override
	public List<EmployeeLeaveManager> getEmployeeLeaveStatus(EmployeeLeaveStatus employeeLeaveStatus) {
		return employeeLeaveManagerRepo.findByEmployeeLeaveStatusOrderByUpdatedAtDesc(employeeLeaveStatus);
	}

	@Override
	public List<EmployeeLeaveManager> getLeaveType(LeaveType leaveType) {
		return employeeLeaveManagerRepo.findByLeaveTypeOrderByUpdatedAtDesc(leaveType);
	}

	@Override
	public List<EmployeeLeaveManager> getLeaveByEmployeeId(Long id) {
		Employee employee = employeeRepo.findById(id).orElseThrow();
		return employeeLeaveManagerRepo.findLeaveByEmployeeId(employee.getId());
	}

	@Override
	public List<EmployeeLeaveManager> getLeaveByEmployee(String name, EmployeeLeaveStatus employeeLeaveStatus) {
		Employee employee = employeeRepo.findByName(name);
		employeeLeaveManagerRepo.findByEmployeeLeaveStatusOrderByUpdatedAtDesc(employeeLeaveStatus);
		return employeeLeaveManagerRepo.findLeaveByEmployeeAndEmployeeLeaveStatus(employee, employeeLeaveStatus);
	}

	@Override
	public List<EmployeeLeaveStatusDto> getEmployeeLeaveStatus(Long id) {
		return employeeLeaveManagerRepo.getEmployeeLeaveStatus(id);
	}

	@Override
	public EmployeeLeaveManager getIsNotificationEnabled(Long id) {
		EmployeeLeaveManager leave = employeeLeaveManagerRepo.findById(id).orElseThrow();
		leave.setIsNotificationEnabled(true);
		leave = employeeLeaveManagerRepo.save(leave);
		return leave;
	}
}