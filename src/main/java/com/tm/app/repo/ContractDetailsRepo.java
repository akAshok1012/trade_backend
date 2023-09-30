package com.tm.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.ContractDetails;
import com.tm.app.entity.Contractor;
import com.tm.app.enums.ContractStatus;

@Repository
public interface ContractDetailsRepo extends JpaRepository<ContractDetails, Long> {

	@Query("SELECT ec FROM ContractDetails ec where LOWER(ec.contractName) like (:search) and ec.contractStatus=:contractStatus")
	Page<ContractDetails> getEmployeeContractList(String search, ContractStatus contractStatus, PageRequest of);

	Page<ContractDetails> findByContractStatusAndContractNameLikeIgnoreCase(ContractStatus contractStatus,
			String search, PageRequest of);

	boolean existsByContractNameAndContractor(String contractName, Contractor contractor);

}
