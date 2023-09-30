package com.tm.app.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.ContractEmployee;

@Repository
public interface ContractEmployeeRepo extends JpaRepository<ContractEmployee, Long> {

	@Query("SELECT cm FROM ContractEmployee cm WHERE LOWER(cm.name) like LOWER(:search)")
	Page<ContractEmployee> getContractEmployeeList(String search, PageRequest of);

	@Query("select ce from ContractEmployee ce where ce.aadhaarNumber in (:aadhaarList) OR ce.phoneNumber in(:phoneNumberList)")
	List<ContractEmployee> getExistingList(List<Long> aadhaarList, List<Long> phoneNumberList);

	List<ContractEmployee> findByIdNotIn(List<Long> idList);

	@Query(value = "select tce.id from t_contract_employees tce left join t_contract_details_employee tcde on(tcde.contract_employee_id=tce.id) where tcde.contract_employee_id is null", nativeQuery = true)
	List<Long> getContractEmployeesNotInContract();

	@Query("SELECT cm FROM ContractEmployee cm join Contractor c on (c.id=cm.contractor) WHERE c.id=:id")
	List<ContractEmployee> getContractEmployeesByContractor(Long id);

	boolean existsByPhoneNumber(Long phoneNumber);

	boolean existsByAadhaarNumber(Long aadhaarNumber);

}