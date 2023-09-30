package com.tm.app.service.impl;

import static com.tm.app.utils.CacheableConstants.EMPLOYEE;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.EmployeeDto;
import com.tm.app.dto.EmployeeIdNameDto;
import com.tm.app.dto.EmployeeUserDto;
import com.tm.app.entity.Employee;
import com.tm.app.entity.EmployeeDailyStatus;
import com.tm.app.entity.EmployeeLeaveManager;
import com.tm.app.entity.EmployeePay;
import com.tm.app.entity.User;
import com.tm.app.repo.EmployeeDailyStatusRepo;
import com.tm.app.repo.EmployeeLeaveManagerRepo;
import com.tm.app.repo.EmployeePayRepo;
import com.tm.app.repo.EmployeeRepo;
import com.tm.app.repo.UserRepository;
import com.tm.app.service.EmployeeService;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmployeeDailyStatusRepo employeeDailyStatusRepo;

	@Autowired
	private EmployeeLeaveManagerRepo employeeLeaveManagerRepo;

	@Autowired
	private EmployeePayRepo employeePayRepo;

	@Override
	@CacheEvict(value = EMPLOYEE, allEntries = true)
	public Employee saveEmployee(EmployeeDto employeeDto) {
		log.info("[EmployeeServiceImpl] saveEmployee starts");

		Employee employee = new Employee();
		try {
			if (Objects.nonNull(employeeDto.getPhoneNumber())
					&& employeeRepo.existsByPhoneNumber(employeeDto.getPhoneNumber())) {
				throw new RuntimeException("PhoneNumber  already exists");
			}
			if (Objects.nonNull(employeeDto.getAadhaarNumber())
					&& employeeRepo.existsByAadhaarNumber(employeeDto.getAadhaarNumber())) {
				throw new RuntimeException("AadhaarNumber  already exists");
			}
			if (StringUtils.isNotEmpty(employeeDto.getEsiNumber())
					&& employeeRepo.existsByEsiNumber(employeeDto.getEsiNumber())) {
				throw new RuntimeException("EsiNumber  already exists");
			}
			if (StringUtils.isNotEmpty(employeeDto.getPanNumber())
					&& employeeRepo.existsByPanNumber(employeeDto.getPanNumber())) {
				throw new RuntimeException("PanNumber  already exists");
			}
			if (Objects.nonNull(employeeDto.getUanNumber())
					&& employeeRepo.existsByUanNumber(employeeDto.getUanNumber())) {
				throw new RuntimeException("UanNumber  already exists");
			}
			if (StringUtils.isNotEmpty(employeeDto.getPfNumber())
					&& employeeRepo.existsByPfNumber(employeeDto.getPfNumber())) {
				throw new RuntimeException("PfNumber  already exists");
			}
			if (StringUtils.isNotEmpty(employeeDto.getEmail()) && employeeRepo.existsByEmail(employeeDto.getEmail())) {
				throw new RuntimeException("Email  already exists");
			}
			BeanUtils.copyProperties(employeeDto, employee);
			employee = employeeRepo.save(employee);
		} catch (Exception e) {
			log.error("[EmployeeServiceImpl] Adding employee failed", e);
			throw new RuntimeException(e.getMessage());
		}
		log.info("[EmployeeServiceImpl] saveEmployee ends");
		return employee;
	}

	@Override
	@Cacheable(value = EMPLOYEE)
	public List<Employee> getEmployees() {
		log.info("[EmployeeServiceImpl] getEmployees starts");
		return employeeRepo.findAll();
	}

	@Override
	@Cacheable(value = EMPLOYEE, key = "#id")
	public Employee getEmployeeById(Long id) {
		log.info("[EmployeeServiceImpl] getEmployeeById starts");
		return employeeRepo.findById(id).orElseThrow();
	}

	@Override
	@CacheEvict(value = EMPLOYEE, allEntries = true)
	public Employee updateEmployee(Long id, EmployeeDto employeeDto) {
		log.info("[EmployeeServiceImpl] updateEmployee starts");

		Employee employee = employeeRepo.findById(id).orElseThrow();
		try {
			if (Objects.nonNull(employeeDto.getPhoneNumber())
					&& employeeRepo.existsByPhoneNumber(employeeDto.getPhoneNumber())
					&& !employee.getPhoneNumber().equals(employeeDto.getPhoneNumber())) {
				throw new RuntimeException("PhoneNumber  already exists");
			}
			if (Objects.nonNull(employeeDto.getAadhaarNumber())
					&& employeeRepo.existsByAadhaarNumber(employeeDto.getAadhaarNumber())
					&& !employee.getAadhaarNumber().equals(employeeDto.getAadhaarNumber())) {
				throw new RuntimeException("AadhaarNumber  already exists");
			}
			if (StringUtils.isNotEmpty(employeeDto.getEsiNumber())
					&& employeeRepo.existsByEsiNumber(employeeDto.getEsiNumber())
					&& !employee.getEsiNumber().equals(employeeDto.getEsiNumber())) {
				throw new RuntimeException("EsiNumber  already exists");
			}
			if (StringUtils.isNotEmpty(employeeDto.getPanNumber())
					&& employeeRepo.existsByPanNumber(employeeDto.getPanNumber())
					&& !employee.getPanNumber().equals(employeeDto.getPanNumber())) {
				throw new RuntimeException("PanNumber  already exists");
			}
			if (Objects.nonNull(employeeDto.getUanNumber())
					&& employeeRepo.existsByUanNumber(employeeDto.getUanNumber())
					&& !employee.getUanNumber().equals(employeeDto.getUanNumber())) {
				throw new RuntimeException("uanNumber  already exists");
			}
			if (StringUtils.isNotEmpty(employeeDto.getPfNumber())
					&& employeeRepo.existsByPfNumber(employeeDto.getPfNumber())
					&& !employee.getPfNumber().equals(employeeDto.getPfNumber())) {
				throw new RuntimeException("PfNumber  already exists");
			}
			if (StringUtils.isNotEmpty(employeeDto.getEmail()) && employeeRepo.existsByEmail(employeeDto.getEmail())
					&& !employee.getEmail().equals(employeeDto.getEmail())) {
				throw new RuntimeException("Email  already exists");
			}
			employee.setName(employeeDto.getName());
			employee.setDesignation(employeeDto.getDesignation());
			employee.setDateOfBirth(employeeDto.getDateOfBirth());
			employee.setEmployeeDepartment(employeeDto.getEmployeeDepartment());
			employee.setDateOfJoining(employeeDto.getDateOfJoining());
			employee.setPfNumber(employeeDto.getPfNumber());
			employee.setEsiNumber(employeeDto.getEsiNumber());
			employee.setPanNumber(employeeDto.getPanNumber());
			employee.setUanNumber(employeeDto.getUanNumber());
			employee.setAadhaarNumber(employeeDto.getAadhaarNumber());
			employee.setPhoneNumber(employeeDto.getPhoneNumber());
			employee.setEmail(employeeDto.getEmail());
			employee.setAddress(employeeDto.getAddress());
			employee.setUpdatedBy(employeeDto.getUpdatedBy());
			employee = employeeRepo.save(employee);
		} catch (Exception e) {
			log.error("[EmployeeServiceImpl] updating employee failed", e);
			throw new RuntimeException(e.getMessage());
		}
		log.info("[EmployeeServiceImpl] updateEmployee ends");
		return employee;
	}

	@Override
	@CacheEvict(value = EMPLOYEE, key = "#id", allEntries = true)
	public void deleteEmployeeById(Long id) {
		log.info("[EmployeeServiceImpl] deleteEmployeeById starts");
		try {
			Employee employee = employeeRepo.findById(id).orElseThrow();
			List<EmployeeDailyStatus> employeeDailyStatus = employeeDailyStatusRepo.findByEmployee(employee);
			List<EmployeeLeaveManager> employeeLeaveManager = employeeLeaveManagerRepo.findByEmployee(employee);
			List<EmployeePay> employeePays = employeePayRepo.findByEmployee(employee);
			List<Long> ids = null;
			if (Objects.nonNull(employeeDailyStatus)) {
				ids = employeeDailyStatus.stream().map(EmployeeDailyStatus::getId).toList();
				employeeDailyStatusRepo.deleteAllById(ids);
			}
			if (Objects.nonNull(employeeLeaveManager)) {
				ids = employeeLeaveManager.stream().map(EmployeeLeaveManager::getId).toList();
				employeeLeaveManagerRepo.deleteAllById(ids);
			}
			if (Objects.nonNull(employeePays)) {
				ids = employeePays.stream().map(EmployeePay::getId).toList();
				employeePayRepo.deleteAllById(ids);
			}
			employeeRepo.deleteById(id);

		} catch (Exception e) {
			log.error("[EmployeeServiceImpl] deleting employee failed", e);
			throw new RuntimeException("Deleting employee failed");
		}
		log.info("[EmployeeServiceImpl] deleteEmployeeById ends");
	}

	@Override
	public List<EmployeeIdNameDto> getEmployeeIdAndName() {
		log.info("[EmployeeServiceImpl] getEmployeeIdAndName starts");
		return employeeRepo.findByIdAndName();
	}

	@Override
	@Cacheable(value = EMPLOYEE)
	public Page<Employee> getEmployeeList(DataFilter dataFilter) {
		log.info("[EmployeeServiceImpl] getEmployeeList starts");
		return employeeRepo.findByNameLikeIgnoreCase(dataFilter.getSearch(), PageRequest.of(dataFilter.getPage(),
				dataFilter.getSize(), Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}

	@Override
	public Employee getEmployeeUser(Long id) {
		log.info("[EmployeeServiceImpl] getEmployeeByUserId starts");
		User user = userRepository.findById(id).orElseThrow();
		return employeeRepo.findById(user.getUserId()).orElseThrow();
	}

	@Override
	public Employee getEmployeeByUserId(Long id) {
		User user = userRepository.findById(id).orElseThrow();
		return employeeRepo.findById(user.getUserId()).orElseThrow();
	}

	@Override
	@CacheEvict(value = EMPLOYEE, key = "#id", allEntries = true)
	public Employee updateEmployeeUser(Long id, EmployeeUserDto employeeUserDto) {
		try {
			User user = userRepository.findById(id).orElseThrow();
			Employee employee = employeeRepo.findById(user.getUserId()).orElseThrow();
			BeanUtils.copyProperties(employeeUserDto, employee);
			return employeeRepo.save(employee);
		} catch (Exception e) {
			log.error("[EMPLOYEE] Employee Updated Failed", e);
			throw new RuntimeException("Employee Updated Failed");
		}
	}
}