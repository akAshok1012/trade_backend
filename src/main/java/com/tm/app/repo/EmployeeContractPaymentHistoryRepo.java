package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.EmployeeContractPayment;
import com.tm.app.entity.EmployeeContractPaymentHistory;
@Repository
public interface EmployeeContractPaymentHistoryRepo extends JpaRepository<EmployeeContractPaymentHistory, Long> {

	List<EmployeeContractPaymentHistory> findByEmployeeContractPayment(EmployeeContractPayment contractPayment);

}
