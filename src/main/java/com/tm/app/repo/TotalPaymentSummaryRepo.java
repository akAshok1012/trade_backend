package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.TotalPaymentSummary;

@Repository
public interface TotalPaymentSummaryRepo extends ReadOnlyViewsRepo<TotalPaymentSummary, Long> {

	@Query(value = "select * from v_total_payment_summary", nativeQuery = true)
	List<TotalPaymentSummary> getTotalPaymentSummary();

}
