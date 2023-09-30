package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.StockCount;

@Repository
public interface StockCountRepo extends StockCountSummaryRepo<StockCount, Long> {

	@Query(value = "select * from v_stock_count", nativeQuery = true)
	List<StockCount> getStockCount(Long id);

	@Query(value = "select * from v_stock_count where brand=?1", nativeQuery = true)
	List<StockCount> getStockCountByBrand(Long id);

}
