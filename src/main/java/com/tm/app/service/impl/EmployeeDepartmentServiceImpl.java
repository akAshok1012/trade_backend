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
import com.tm.app.dto.EmployeeDepartmentDto;
import com.tm.app.dto.EmployeeDepartmentIdNameDto;
import com.tm.app.entity.EmployeeDepartment;
import com.tm.app.repo.EmployeeDepartmentRepo;
import com.tm.app.service.EmployeeDepartmentService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeDepartmentServiceImpl implements EmployeeDepartmentService {

	@Autowired
	private EmployeeDepartmentRepo employeeDepartmentRepo;

	@Override
	@Transactional
	public EmployeeDepartment saveEmployeeDepartment(EmployeeDepartmentDto employeeDepartmentDto) {
		EmployeeDepartment empDepartment = new EmployeeDepartment();
		try {
			if (Objects.nonNull(employeeDepartmentDto.getDepartmentName())
					&& employeeDepartmentRepo.existsByDepartmentNameIgnoreCase(employeeDepartmentDto.getDepartmentName())) {
				throw new RuntimeException("DepartmentName  already exists");
			}
			BeanUtils.copyProperties(employeeDepartmentDto, empDepartment);
			empDepartment = employeeDepartmentRepo.save(empDepartment);
		} catch (Exception e) {
			log.error("[EMPLOYEE] Adding employee department failed", e);
			throw new RuntimeException(e.getMessage());
		}
		return empDepartment;
	}

	@Override
	public List<EmployeeDepartment> getEmployeeDepartment() {
		return employeeDepartmentRepo.findAll();
	}

	@Override
	public EmployeeDepartment getEmployeeDepartmentById(Long id) {
		return employeeDepartmentRepo.findById(id).orElseThrow();
	}

	@Override
	@Transactional
	public EmployeeDepartment updateEmployeeDepartment(Long id, EmployeeDepartmentDto employeeDepartmentDto) {
		EmployeeDepartment department = employeeDepartmentRepo.findById(id).orElseThrow();
		try {
			if (Objects.nonNull(employeeDepartmentDto.getDepartmentName())
					&& employeeDepartmentRepo.existsByDepartmentNameIgnoreCase(employeeDepartmentDto.getDepartmentName())
					&& !department.getDepartmentName().equals(employeeDepartmentDto.getDepartmentName())) {
				throw new RuntimeException("DepartmentName  already exists");
			}
			department.setDepartmentName(employeeDepartmentDto.getDepartmentName());
			department.setUpdatedBy(employeeDepartmentDto.getUpdatedBy());
			department = employeeDepartmentRepo.save(department);
		} catch (Exception e) {
			log.error("[EMPLOYEE] Updating employee department failed", e);
			throw new RuntimeException(e.getMessage());
		}
		return department;
	}

	@Override
	public void deleteDepartmentById(Long id) {
		try {
			employeeDepartmentRepo.deleteById(id);
		} catch (Exception e) {
			log.error("[EMPLOYEE] Deleting employee department failed", e);
			throw new RuntimeException("Deleting employee department failed");
		}
	}

	@Override
	public List<EmployeeDepartmentIdNameDto> getEmployeeDepartmentName() {
		return employeeDepartmentRepo.findByDepartmentIdName();
	}

	@Override
	public Page<EmployeeDepartment> getEmployeeDepartmentList(DataFilter dataFilter) {
		return employeeDepartmentRepo.findByDepartmentNameLikeIgnoreCase(dataFilter.getSearch(),
				PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
						Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}
}