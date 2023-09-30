package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.WeeklySalesAndOrdersAndPaymentCount;

@Repository
public interface WeeklySalesAndOrdersAndPaymentRepo
		extends ReadOnlyViewsRepo<WeeklySalesAndOrdersAndPaymentCount, Long> {

	@Query(value = "select * from v_weekly_orders_sales_payment_count", nativeQuery = true)
	List<WeeklySalesAndOrdersAndPaymentCount> getWeeklySalesAndOrdersAndPaymentCount();

}
