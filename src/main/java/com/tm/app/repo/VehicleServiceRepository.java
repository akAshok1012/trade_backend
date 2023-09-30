package com.tm.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.VehicleService;

@Repository
public interface VehicleServiceRepository extends JpaRepository<VehicleService, Long> {

	@Query("SELECT vs FROM VehicleService vs join VehicleDetails vd on(vs.vehicleDetails=vd.id) where LOWER(vd.vehicleRegistrationNumber) like LOWER(:search)")
	Page<VehicleService> getVehicleServicesList(String search, PageRequest of);

}
