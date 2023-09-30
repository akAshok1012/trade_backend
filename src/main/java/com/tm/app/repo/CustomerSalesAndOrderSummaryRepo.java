package com.tm.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.CustomerSalesAndOrderSummary;

@Repository
public interface CustomerSalesAndOrderSummaryRepo extends ReadOnlyViewsRepo<CustomerSalesAndOrderSummary, Long> {

	@Query(value = "select * from v_customer_sales_orders_summary WHERE Lower(client_name) like LOWER(:search)", nativeQuery = true)
	Page<CustomerSalesAndOrderSummary> getCustomerSalesAndOrderSummary(PageRequest of, String search);

}
