package com.tm.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.VehicleServiceType;

@Repository
public interface VehicleServiceTypeRepo extends JpaRepository<VehicleServiceType, Long> {

	Page<VehicleServiceType> findByServiceTypeLikeIgnoreCase(String search, PageRequest of);

	boolean existsByServiceTypeEqualsIgnoreCase(String serviceType);

}
