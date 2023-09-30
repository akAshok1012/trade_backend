package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.MonthlySalesAndOrdersAndPaymentCount;

@Repository
public interface MonthlySalesAndOrdersAndPaymentRepo
		extends ReadOnlyViewsRepo<MonthlySalesAndOrdersAndPaymentCount, Long> {

	@Query(value = "select * from v_monthly_orders_sales_payment_count", nativeQuery = true)
	List<MonthlySalesAndOrdersAndPaymentCount> getMonthlySalesAndOrdersAndPaymentCount();

}
