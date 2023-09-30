package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.DailySalesAndOrdersAndPaymentCount;

@Repository
public interface DailySalesAndOrdersAndPaymentRepo extends ReadOnlyViewsRepo<DailySalesAndOrdersAndPaymentCount, Long> {

	@Query(value = "select * from v_daily_orders_sales_payment_count", nativeQuery = true)
	List<DailySalesAndOrdersAndPaymentCount> getDailySalesAndOrdersCount();

}
