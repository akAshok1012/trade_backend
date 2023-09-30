package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.EmployeeContractPaymentDto;
import com.tm.app.entity.EmployeeContractPayment;
import com.tm.app.entity.EmployeeContractPaymentHistory;
import com.tm.app.enums.ContractStatus;

public interface EmployeeContractPaymentService {

	EmployeeContractPayment saveEmployeeContractPayment(EmployeeContractPaymentDto employeeContractPaymentDto);

	List<EmployeeContractPayment> getEmployeeContractPayments();

	EmployeeContractPayment getEmployeeContractPaymentById(Long id);

	EmployeeContractPayment updateEmployeeContractPayment(Long id,
			EmployeeContractPaymentDto employeeContractPaymentDto);

	void deleteEmployeeContractPaymentById(Long id);

	Page<EmployeeContractPayment> getEmployeeContractPaymentList(DataFilter dataFilter, ContractStatus contractStatus);

	List<EmployeeContractPaymentHistory> getAllEmployeeContractPaymentHistory(Long id);

}
