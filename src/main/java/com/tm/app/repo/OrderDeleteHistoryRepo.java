package com.tm.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.OrderDeleteHistory;

@Repository
public interface OrderDeleteHistoryRepo extends JpaRepository<OrderDeleteHistory, Long> {

}
