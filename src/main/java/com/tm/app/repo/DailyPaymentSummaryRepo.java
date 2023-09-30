package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.DailyPaymentSummary;

@Repository
public interface DailyPaymentSummaryRepo extends ReadOnlyViewsRepo<DailyPaymentSummary, Long> {

	@Query(value = "select * from v_customer_payment_daily_summary", nativeQuery = true)
	List<DailyPaymentSummary> getDailyPaymentSummary();
}
