package com.tm.app.service.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.EmployeeContractPaymentDto;
import com.tm.app.entity.EmployeeContractPayment;
import com.tm.app.entity.EmployeeContractPaymentHistory;
import com.tm.app.enums.ContractStatus;
import com.tm.app.enums.PaymentStatus;
import com.tm.app.repo.ContractDetailsRepo;
import com.tm.app.repo.EmployeeContractPaymentHistoryRepo;
import com.tm.app.repo.EmployeeContractPaymentRepo;
import com.tm.app.service.EmployeeContractPaymentService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class EmployeeContractPaymentServiceImpl implements EmployeeContractPaymentService {

	@Autowired
	private EmployeeContractPaymentRepo employeeContractPaymentRepo;

	@Autowired
	private EmployeeContractPaymentHistoryRepo contractPaymentHistoryRepo;

	@Autowired
	private ContractDetailsRepo contractDetailsRepo;

	@Override
	public EmployeeContractPayment saveEmployeeContractPayment(EmployeeContractPaymentDto employeeContractPaymentDto) {
		log.info("[EMPLOYEE_CONTRACT_PAYMENT] saveEmployeeContractPayment starts");
		try {
			EmployeeContractPayment employeeContractPayment = new EmployeeContractPayment();
			BeanUtils.copyProperties(employeeContractPaymentDto, employeeContractPayment);
			return employeeContractPaymentRepo.save(employeeContractPayment);
		} catch (Exception e) {
			log.error("[EMPLOYEE_CONTRACT_PAYMENT] saveEmployeeContractPayment failed", e);
			throw new RuntimeException("EmployeeContractPayment save failed");
		}
	}

	@Override
	public List<EmployeeContractPayment> getEmployeeContractPayments() {
		return employeeContractPaymentRepo.findAll();
	}

	@Override
	public EmployeeContractPayment getEmployeeContractPaymentById(Long id) {
		return employeeContractPaymentRepo.findById(id).orElseThrow();
	}

	@Override
	public EmployeeContractPayment updateEmployeeContractPayment(Long id,
			EmployeeContractPaymentDto employeeContractPaymentDto) {
		log.info("[EMPLOYEE_CONTRACT_PAYMENT] updateEmployeeContractPayment starts");
		try {
			EmployeeContractPayment contractPayment = employeeContractPaymentRepo.findById(id).orElseThrow();
			contractPayment.setAmountPaid(contractPayment.getAmountPaid() + employeeContractPaymentDto.getAmountPaid());
			contractPayment.setAmountBalance(contractPayment.getTotalAmount() - contractPayment.getAmountPaid());
			contractPayment.setContractDetails(employeeContractPaymentDto.getContractDetails());
			contractPayment.setUpdatedBy(employeeContractPaymentDto.getUpdatedBy());
			contractPayment.setPaymentDate(employeeContractPaymentDto.getPaymentDate());
			contractPayment.setPaymentBasis(employeeContractPaymentDto.getPaymentBasis());
			if (contractPayment.getAmountBalance() == 0) {
				contractPayment.setPaymentStatus(PaymentStatus.PAID);
			} else if (Objects.equals(contractPayment.getAmountBalance(), contractPayment.getTotalAmount())) {
				contractPayment.setPaymentStatus(PaymentStatus.UNPAID);
			} else {
				contractPayment.setPaymentStatus(PaymentStatus.PARTIAL);
			}
			if (contractPayment.getContractDetails().getEndDate().before(Date.valueOf(LocalDate.now()))
					&& !contractPayment.getPaymentStatus().equals(PaymentStatus.PAID)) {
				contractPayment.getContractDetails().setContractStatus(ContractStatus.PARTIALY_CLOSED);
			} else if (Objects.equals(contractPayment.getContractDetails().getEndDate().toString(),
					LocalDate.now().toString()) && !contractPayment.getPaymentStatus().equals(PaymentStatus.PAID)) {
				contractPayment.getContractDetails().setContractStatus(ContractStatus.PARTIALY_CLOSED);
			} else if (Objects.equals(contractPayment.getContractDetails().getEndDate().toString(),
					LocalDate.now().toString()) && contractPayment.getPaymentStatus().equals(PaymentStatus.PAID)) {
				contractPayment.getContractDetails().setContractStatus(ContractStatus.CLOSED);
			} else if (contractPayment.getContractDetails().getEndDate().before(Date.valueOf(LocalDate.now()))
					&& contractPayment.getPaymentStatus().equals(PaymentStatus.PAID)) {
				contractPayment.getContractDetails().setContractStatus(ContractStatus.CLOSED);
			} else if ((contractPayment.getContractDetails().getEndDate().after(Date.valueOf(LocalDate.now()))
					&& Objects.equals(contractPayment.getContractDetails().getStartDate().toString(),
							LocalDate.now().toString()))
					|| (contractPayment.getContractDetails().getEndDate().after(Date.valueOf(LocalDate.now()))
							&& contractPayment.getContractDetails().getStartDate()
									.before(Date.valueOf(LocalDate.now())))
					|| Objects.equals(contractPayment.getContractDetails().getStartDate().toString(),
							LocalDate.now().toString())) {
				contractPayment.getContractDetails().setContractStatus(ContractStatus.ONGOING);
			} else if ((contractPayment.getContractDetails().getEndDate().after(Date.valueOf(LocalDate.now()))
					&& contractPayment.getContractDetails().getStartDate().after(Date.valueOf(LocalDate.now())))
					|| (contractPayment.getContractDetails().getStartDate().after(Date.valueOf(LocalDate.now())))
					|| (contractPayment.getContractDetails().getEndDate().after(Date.valueOf(LocalDate.now())))) {
				contractPayment.getContractDetails().setContractStatus(ContractStatus.NOT_STARTED);
			} else {
				contractPayment.getContractDetails().setContractStatus(ContractStatus.ONGOING);
			}
			contractDetailsRepo.save(contractPayment.getContractDetails());
			contractPayment.setNotes(employeeContractPaymentDto.getNotes());
			contractPayment = employeeContractPaymentRepo.save(contractPayment);
			EmployeeContractPaymentHistory contractPaymentHistory = new EmployeeContractPaymentHistory();
			contractPaymentHistory.setAmountPaid(employeeContractPaymentDto.getAmountPaid());
			contractPaymentHistory.setEmployeeContractPayment(contractPayment);
			contractPaymentHistory.setNotes(contractPayment.getNotes());
			contractPaymentHistory.setPaymentBasis(contractPayment.getPaymentBasis());
			contractPaymentHistory.setPaymentDate(contractPayment.getPaymentDate());
			contractPaymentHistory.setUpdatedBy(contractPayment.getUpdatedBy());
			contractPaymentHistoryRepo.save(contractPaymentHistory);
			return contractPayment;
		} catch (Exception e) {
			log.error("[EMPLOYEE_CONTRACT_PAYMENT] updateEmployeeContractPayment failed", e);
			throw new RuntimeException("UpdateEmployeeContractPayment failed");
		}
	}

	@Override
	public void deleteEmployeeContractPaymentById(Long id) {
		employeeContractPaymentRepo.deleteById(id);
	}

	@Override
	public Page<EmployeeContractPayment> getEmployeeContractPaymentList(DataFilter dataFilter,
			ContractStatus contractStatus) {
		return employeeContractPaymentRepo.getEmployeeContractPaymentList(dataFilter.getSearch(), contractStatus,
				PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
						Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}

	@Override
	public List<EmployeeContractPaymentHistory> getAllEmployeeContractPaymentHistory(Long id) {
		EmployeeContractPayment contractPayment = employeeContractPaymentRepo.findById(id).orElseThrow();
		return contractPaymentHistoryRepo.findByEmployeeContractPayment(contractPayment);
	}
}