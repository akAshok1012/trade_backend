package com.tm.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.VehicleDetails;

@Repository
public interface VehicleDetailsRepo extends JpaRepository<VehicleDetails, Long> {

	Page<VehicleDetails> findByVehicleRegistrationNumberLikeIgnoreCase(String search, PageRequest of);

	boolean existsByVehicleRegistrationNumberEqualsIgnoreCase(String vehicleRegistrationNumber);

	boolean existsByChassisNumber(String chassisNumber);

	boolean existsByRcNumber(String rcNumber);

	boolean existsByPolicyNo(String policyNo);

}
