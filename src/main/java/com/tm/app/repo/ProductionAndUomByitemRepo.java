package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.ProductionAndUomByItem;

@Repository
public interface ProductionAndUomByitemRepo extends ReadOnlyViewsRepo<ProductionAndUomByItem, Long> {

	@Query(value = "select * from v_production_items_count", nativeQuery = true)
	List<ProductionAndUomByItem> getAllProductionAndUomByItem();

	@Query(value = "select * from v_production_items_count where itemid=?1", nativeQuery = true)
	List<ProductionAndUomByItem> getProductionAndUomByItem(Long id);

}
