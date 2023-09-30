package com.tm.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.VehicleServiceHistory;

@Repository
public interface VehicleServiceHistoryRepo extends JpaRepository<VehicleServiceHistory, Long> {

}
