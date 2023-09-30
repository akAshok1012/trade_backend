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
import com.tm.app.dto.EmployeePayDto;
import com.tm.app.entity.Employee;
import com.tm.app.entity.EmployeePay;
import com.tm.app.entity.User;
import com.tm.app.repo.EmployeePayRepo;
import com.tm.app.repo.EmployeeRepo;
import com.tm.app.repo.UserRepository;
import com.tm.app.service.EmployeePayService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class EmployeePayServiceImpl implements EmployeePayService {

	@Autowired
	private EmployeePayRepo employeePayRepo;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmployeeRepo employeeRepo;

	@Override
	public List<EmployeePay> getEmployeePays() {
		log.info("[EmployeePayServiceImpl] getEmployeePays starts...");

		List<EmployeePay> employeePays = null;
		try {
			employeePays = employeePayRepo.findAll();
		} catch (Exception e) {
			log.info("[EmployeePayServiceImpl] getEmployeePays falied...");
			throw new RuntimeException("[EmployeePayServiceImpl] getEmployeePays falied...", e);
		}
		return employeePays;
	}

	@Override
	public EmployeePay saveEmployeePay(EmployeePayDto employeePayDto) {
		log.info("[EmployeePayServiceImpl] saveEmployeePay starts...");

		EmployeePay employeePay = new EmployeePay();
		if (employeePayRepo.existsByEmployee(employeePayDto.getEmployee())) {
			throw new RuntimeException("Employee Pay  Already exist!. ");
		}
		BeanUtils.copyProperties(employeePayDto, employeePay);
		employeePay = employeePayRepo.save(employeePay);
		log.info("[EmployeePayServiceImpl] saveEmployeePay ends...");
		return employeePay;
	}

	@Override
	public EmployeePay getEmployeePayById(Long id) {
		log.info("[EmployeePayServiceImpl] getEmployeePayById starts...");
		return employeePayRepo.findById(id).orElseThrow();
	}

	@Override
	public void deleteEmployeePayById(Long id) {
		log.info("[EmployeePayServiceImpl] deleteEmployeePayById starts...");

		try {
			employeePayRepo.deleteById(id);
		} catch (Exception e) {
			log.error("[EMPLOYEE] deleting employee pay failed", e);
			throw new RuntimeException("Deleting employee pay failed");
		}
		log.info("[EmployeePayServiceImpl] deleteEmployeePayById ends...");
	}

	@Transactional
	@Override
	public EmployeePay updateEmployeePay(Long id, EmployeePayDto employeePayDto) {
		log.info("[EmployeePayServiceImpl] updateEmployeePay starts...");
		EmployeePay employeePay = employeePayRepo.findById(id).orElseThrow();
		try {
			employeePay.setEmployee(employeePayDto.getEmployee());
			employeePay.setBasicPay(Objects.nonNull(employeePayDto.getBasicPay()) ? employeePayDto.getBasicPay() : 0);
			employeePay.setHra(Objects.nonNull(employeePayDto.getHra()) ? employeePayDto.getHra() : 0);
			employeePay.setDa(Objects.nonNull(employeePayDto.getDa()) ? employeePayDto.getDa() : 0);
			employeePay.setConveyanceAllowance(
					Objects.nonNull(employeePayDto.getConveyanceAllowance()) ? employeePayDto.getConveyanceAllowance()
							: 0);
			employeePay.setMedicalAllowance(
					Objects.nonNull(employeePayDto.getMedicalAllowance()) ? employeePayDto.getMedicalAllowance() : 0);
			employeePay.setSpecialAllowance(
					Objects.nonNull(employeePayDto.getSpecialAllowance()) ? employeePayDto.getSpecialAllowance() : 0);
			employeePay.setBonus(Objects.nonNull(employeePayDto.getBonus()) ? employeePayDto.getBonus() : 0);
			employeePay.setEpf(Objects.nonNull(employeePayDto.getEpf()) ? employeePayDto.getEpf() : 0);
			employeePay.setEsi(Objects.nonNull(employeePayDto.getEsi()) ? employeePayDto.getEsi() : 0);
			employeePay.setIta(Objects.nonNull(employeePayDto.getIta()) ? employeePayDto.getIta() : 0);
			employeePay.setDeductions(employeePay.getEpf() + employeePay.getEsi() + employeePay.getIta());
			employeePay.setEarnings(employeePay.getBasicPay() + employeePay.getBonus() + employeePay.getHra()
					+ employeePay.getDa() + employeePay.getConveyanceAllowance() + employeePay.getMedicalAllowance()
					+ employeePay.getSpecialAllowance());
			employeePay.setNetPay(employeePay.getEarnings() - employeePay.getDeductions());
			employeePay.setUpdatedBy(employeePayDto.getUpdatedBy());
			employeePay = employeePayRepo.save(employeePay);
		} catch (Exception e) {
			log.error("[EMPLOYEE] updating employee pay failed", e);
			throw new RuntimeException("Updating employee pay failed");
		}
		log.info("[EmployeePayServiceImpl] updateEmployeePay ends...");
		return employeePay;
	}

	@Override
	public List<EmployeePay> getEmployeePayByEmployee(Long empId) {
		User user = userRepository.findById(empId).orElseThrow();
		Employee employee = employeeRepo.findById(user.getUserId()).orElseThrow();
		return employeePayRepo.findByEmployee(employee);
	}

	@Override
	public Page<EmployeePay> getEmployeePayList(DataFilter dataFilter) {
		Page<EmployeePay> employeePayList = employeePayRepo.getEmployeePayList(dataFilter.getSearch(),
				PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
						Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
		employeePayList.getContent().stream().forEach(r -> {
			r.setDepartmentName(r.getEmployee().getEmployeeDepartment().getDepartmentName());
		});

		return employeePayList;
	}
}