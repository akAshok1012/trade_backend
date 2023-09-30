package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.CustomerPaymentSummary;

@Repository
public interface CustomerPaymentSummaryRepo extends ReadOnlyViewsRepo<CustomerPaymentSummary, Long> {

	@Query(value = "select * from v_customers_payment_summary  where customer_id=?1", nativeQuery = true)
	List<CustomerPaymentSummary> getCustomerPaymentSummaryByCustomer(Long id);

	@Query(value = "select * from v_customers_payment_summary", nativeQuery = true)
	List<CustomerPaymentSummary> getCustomerPaymentSummary();

}
