package com.tm.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.PaymentType;

@Repository
public interface PaymentTypeRepo extends JpaRepository<PaymentType, Long> {

	PaymentType getPaymentTypeById(Long id);

	Page<PaymentType> findByPaymentTypeLikeIgnoreCase(String search, PageRequest of);

	boolean existsByPaymentTypeEqualsIgnoreCase(String paymentType);

}
