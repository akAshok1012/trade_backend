package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.ProductionCountByItem;

@Repository
public interface ProductionCountAndUomRepo extends ReadOnlyViewsRepo<ProductionCountByItem, Long> {

	@Query(value = "select * from v_production_count_by_item", nativeQuery = true)
	List<ProductionCountByItem> getProductionCounts();

	@Query(value = "select * from v_production_count_by_item where itemid=?1", nativeQuery = true)
	List<ProductionCountByItem> getProductionCountByItem(Long id);

}
