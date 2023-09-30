package com.tm.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.ContractDetails;
import com.tm.app.entity.EmployeeContractPayment;
import com.tm.app.enums.ContractStatus;
@Repository
public interface EmployeeContractPaymentRepo extends JpaRepository<EmployeeContractPayment, Long>{

	@Query("SELECT ecp FROM EmployeeContractPayment ecp join ContractDetails cd on (ecp.contractDetails=cd.id) where LOWER(cd.contractName) like LOWER(:search) and cd.contractStatus=:contractStatus")
	Page<EmployeeContractPayment> getEmployeeContractPaymentList(String search, ContractStatus contractStatus, PageRequest of);

	EmployeeContractPayment findByContractDetails(ContractDetails contractDetails);

}
