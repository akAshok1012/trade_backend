package com.tm.app.service.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.ContractDetailsDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.entity.ContractDetails;
import com.tm.app.entity.EmployeeContractPayment;
import com.tm.app.enums.ContractStatus;
import com.tm.app.enums.PaymentStatus;
import com.tm.app.repo.ContractDetailsRepo;
import com.tm.app.repo.EmployeeContractPaymentRepo;
import com.tm.app.service.ContractDetailsService;
import com.tm.app.utils.APIResponseConstants;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class ContractDetailsServiceImpl implements ContractDetailsService {

	@Autowired
	private ContractDetailsRepo contractDetailsRepo;

	@Autowired
	private EmployeeContractPaymentRepo contractPaymentRepo;

	@Override
	public ContractDetails saveEmployeeContract(ContractDetailsDto employeeContratDto) {
		log.info("[EMPLOYEE_CONTRACT] saveEmployeeContract starts");
		try {
			ContractDetails contractDetails = new ContractDetails();
			BeanUtils.copyProperties(employeeContratDto, contractDetails);
			if (contractDetailsRepo.existsByContractNameAndContractor(contractDetails.getContractName(),
					contractDetails.getContractor())) {
				throw new RuntimeException(
						String.format(APIResponseConstants.ALREADY_EXISTS, contractDetails.getContractName()));
			}
			EmployeeContractPayment contractPayment = new EmployeeContractPayment();
			contractPayment.setAmountBalance(contractDetails.getContractAmount());
			contractPayment.setAmountPaid(0F);
			contractPayment.setContractDetails(contractDetails);
			contractPayment.setTotalAmount(contractDetails.getContractAmount());
			contractPayment.setUpdatedBy(contractDetails.getUpdatedBy());
			contractPayment.setPaymentStatus(PaymentStatus.UNPAID);
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
				contractPayment.getContractDetails().setContractStatus(ContractStatus.UPCOMING);
			} else {
				contractPayment.getContractDetails().setContractStatus(ContractStatus.ONGOING);
			}
			contractPaymentRepo.save(contractPayment);
			return contractDetailsRepo.save(contractDetails);
		} catch (Exception e) {
			log.error("[EMPLOYEE_CONTRACT] save employeeContract failed", e);
			throw new RuntimeException("EmployeeContract save failed");
		}
	}

	@Override
	public List<ContractDetails> getEmployeeContracts() {
		return contractDetailsRepo.findAll();
	}

	@Override
	public ContractDetails getEmployeeContractById(Long id) {
		return contractDetailsRepo.findById(id).orElseThrow();
	}

	@Override
	public ContractDetails updateEmployeeContract(Long id, ContractDetailsDto employeeContratDto) {
		log.info("[EMPLOYEE_CONTRACT] updateEmployeeContract starts");
		try {
			ContractDetails contractDetails = contractDetailsRepo.findById(id).orElseThrow();
			BeanUtils.copyProperties(employeeContratDto, contractDetails);
			if (contractDetailsRepo.existsByContractNameAndContractor(contractDetails.getContractName(),
					contractDetails.getContractor())
					&& !contractDetails.getContractName().equals(employeeContratDto.getContractName())
					&& !contractDetails.getContractor().equals(employeeContratDto.getContractor())) {
				throw new RuntimeException(
						String.format(APIResponseConstants.ALREADY_EXISTS, contractDetails.getContractName()));
			}
			EmployeeContractPayment contractPayment = contractPaymentRepo.findByContractDetails(contractDetails);
			contractPayment.setAmountPaid(contractPayment.getAmountPaid());
			contractPayment.setAmountBalance(contractDetails.getContractAmount() - contractPayment.getAmountPaid());
			contractPayment.setContractDetails(contractDetails);
			contractPayment.setTotalAmount(contractDetails.getContractAmount());
			contractPayment.setUpdatedBy(contractDetails.getUpdatedBy());
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
				contractPayment.getContractDetails().setContractStatus(ContractStatus.UPCOMING);
			} else {
				contractPayment.getContractDetails().setContractStatus(ContractStatus.ONGOING);
			}
			contractPaymentRepo.save(contractPayment);
			contractDetails = contractDetailsRepo.save(contractDetails);
			return contractDetails;
		} catch (Exception e) {
			log.error("[EMPLOYEE_CONTRACT] update employee contract failed", e);
			throw new RuntimeException("Update employee contract failed");
		}
	}

	@Override
	public void deleteEmployeeContractById(Long id) {
		contractDetailsRepo.deleteById(id);
	}

	@Override
	public Page<ContractDetails> getEmployeeContractList(DataFilter dataFilter, ContractStatus contractStatus) {
		return contractDetailsRepo.findByContractStatusAndContractNameLikeIgnoreCase(contractStatus,
				dataFilter.getSearch(), PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
						Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}

	@Override
	public void updateContractStatusJob() {
		List<EmployeeContractPayment> contractPaymentDetails = contractPaymentRepo.findAll();
		List<ContractDetails> contractDetails = new ArrayList<>();
		for (EmployeeContractPayment employeeContractPayment : contractPaymentDetails) {
			if (employeeContractPayment.getContractDetails().getEndDate().before(Date.valueOf(LocalDate.now()))
					&& !employeeContractPayment.getPaymentStatus().equals(PaymentStatus.PAID)) {
				employeeContractPayment.getContractDetails().setContractStatus(ContractStatus.PARTIALY_CLOSED);
			} else if (Objects.equals(employeeContractPayment.getContractDetails().getEndDate().toString(),
					LocalDate.now().toString())
					&& !employeeContractPayment.getPaymentStatus().equals(PaymentStatus.PAID)) {
				employeeContractPayment.getContractDetails().setContractStatus(ContractStatus.PARTIALY_CLOSED);
			} else if (Objects.equals(employeeContractPayment.getContractDetails().getEndDate().toString(),
					LocalDate.now().toString())
					&& employeeContractPayment.getPaymentStatus().equals(PaymentStatus.PAID)) {
				employeeContractPayment.getContractDetails().setContractStatus(ContractStatus.CLOSED);
			} else if (employeeContractPayment.getContractDetails().getEndDate().before(Date.valueOf(LocalDate.now()))
					&& employeeContractPayment.getPaymentStatus().equals(PaymentStatus.PAID)) {
				employeeContractPayment.getContractDetails().setContractStatus(ContractStatus.CLOSED);
			} else if ((employeeContractPayment.getContractDetails().getEndDate().after(Date.valueOf(LocalDate.now()))
					&& Objects.equals(employeeContractPayment.getContractDetails().getStartDate().toString(),
							LocalDate.now().toString()))
					|| (employeeContractPayment.getContractDetails().getEndDate().after(Date.valueOf(LocalDate.now()))
							&& employeeContractPayment.getContractDetails().getStartDate()
							.before(Date.valueOf(LocalDate.now())))
					|| Objects.equals(employeeContractPayment.getContractDetails().getStartDate().toString(),
							LocalDate.now().toString())) {
				employeeContractPayment.getContractDetails().setContractStatus(ContractStatus.ONGOING);
			} else if ((employeeContractPayment.getContractDetails().getEndDate().after(Date.valueOf(LocalDate.now()))
					&& employeeContractPayment.getContractDetails().getStartDate().after(Date.valueOf(LocalDate.now())))
					|| (employeeContractPayment.getContractDetails().getStartDate()
							.after(Date.valueOf(LocalDate.now())))
					|| (employeeContractPayment.getContractDetails().getEndDate()
							.after(Date.valueOf(LocalDate.now())))) {
				employeeContractPayment.getContractDetails().setContractStatus(ContractStatus.UPCOMING);
			} else {
				employeeContractPayment.getContractDetails().setContractStatus(ContractStatus.ONGOING);
			}
			contractDetails.add(employeeContractPayment.getContractDetails());
		}
		contractDetailsRepo.saveAll(contractDetails);
	}
}