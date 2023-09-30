package com.tm.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.VehicleType;

@Repository
public interface VehicleTypeRepo extends JpaRepository<VehicleType, Long> {

	Page<VehicleType> findByVehicleTypeNameLikeIgnoreCase(String search, PageRequest of);

	boolean existsByVehicleTypeNameEqualsIgnoreCase(String vehicleTypeName);

}
