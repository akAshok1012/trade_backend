package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.SalesAndOrderCount;

@Repository
public interface SalesAndOrderCountRepo extends TotalSalesAndOrderCountRepo<SalesAndOrderCount, Long> {

	@Query(value = "select soc from SalesAndOrderCount soc Order By date Desc ")
	List<SalesAndOrderCount> getSalesAndOrdersCount();

}
