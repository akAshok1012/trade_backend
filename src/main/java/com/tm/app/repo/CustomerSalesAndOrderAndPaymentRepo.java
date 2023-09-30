package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.CustomerSalesAndOrderAndPaymentSummary;

@Repository
public interface CustomerSalesAndOrderAndPaymentRepo
		extends ReadOnlyViewsRepo<CustomerSalesAndOrderAndPaymentSummary, Long> {

	@Query(value = "select * from v_customer_sales_orders_payment_summary where id=?1", nativeQuery = true)
	List<CustomerSalesAndOrderAndPaymentSummary> getCustomerSalesAndOrderAndPaymentByCustomer(Long id);

	@Query(value = "select * from v_customer_sales_orders_payment_summary", nativeQuery = true)
	List<CustomerSalesAndOrderAndPaymentSummary> getCustomerSalesAndOrderAndPayment();

	@Query(value = "select * from v_customer_sales_orders_payment_summary where id=?1", nativeQuery = true)
	CustomerSalesAndOrderAndPaymentSummary getCustomerSalesAndOrderAndPayments(Long id);

}
