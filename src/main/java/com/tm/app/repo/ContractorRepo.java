package com.tm.app.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.Contractor;
@Repository
public interface ContractorRepo extends JpaRepository<Contractor, Long> {

	@Query("SELECT c FROM Contractor c where LOWER(c.name) like LOWER(:search)")
	Page<Contractor> getContractorList(String search, PageRequest of);

	@Query("Select id from Contractor")
	List<Long> getAllContractor();

	boolean existsByPhoneNumber(Long phoneNumber);

	boolean existsByAadhaarNumber(Long aadhaarNumber);

	boolean existsByEmail(String email);

	boolean existsByPanNumber(String panNumber);
}
