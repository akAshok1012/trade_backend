package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.Customer;
import com.tm.app.entity.PaymentHistory;

@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {

	@Query("select tph from PaymentHistory tph join Payment tp on(tp.id=tph.payment) WHERE tp.customer =:customerId order by tph.paymentDateTime desc limit 10")
	List<PaymentHistory> getPaymentHistoryByCustomer(Customer customerId);

}
