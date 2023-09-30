package com.tm.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.Maintenance;

@Repository
public interface MaintenanceRepo extends JpaRepository<Maintenance, Long> {

	Maintenance getMaintenanceById(Long id);

	@Query(value = "SELECT m FROM Maintenance m where LOWER(m.machineryId.serialNumber) LIKE LOWER(:search)")
	Page<Maintenance> findBySerialNumber(String search, PageRequest of);
}
