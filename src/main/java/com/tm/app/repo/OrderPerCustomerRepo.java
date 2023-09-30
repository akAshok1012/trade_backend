package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.OrdersPerCustomer;

@Repository
public interface OrderPerCustomerRepo extends ReadOnlyViewsRepo<OrdersPerCustomer, Long> {

	@Query(value = "select * from v_customer_count_order_per_month where yr=?1", nativeQuery = true)
	List<OrdersPerCustomer> getOrdersPerCustomer(Integer year);

}
