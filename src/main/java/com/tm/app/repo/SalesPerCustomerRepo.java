package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.SalesPerCustomer;

@Repository
public interface SalesPerCustomerRepo extends ReadOnlyViewsRepo<SalesPerCustomer, Long> {

	@Query(value = "select * from v_customer_count_sales_per_month where yr=?1", nativeQuery = true)
	List<SalesPerCustomer> getSalesPerCustomer(Integer yr);

}
