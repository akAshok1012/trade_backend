package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.MonthlyPaymentSummary;

@Repository
public interface MonthlyPaymentSummaryRepo extends ReadOnlyViewsRepo<MonthlyPaymentSummary, Long> {

	@Query(value = "select * from v_customer_payment_monthly_summary", nativeQuery = true)
	List<MonthlyPaymentSummary> getMonthlyPaymentSummary();

}
