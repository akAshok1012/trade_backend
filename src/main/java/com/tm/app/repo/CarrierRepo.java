package com.tm.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.Carrier;

@Repository
public interface CarrierRepo extends JpaRepository<Carrier, Long> {

}
