package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.ProductionCount;

@Repository
public interface ProductionCountRepo extends ReadOnlyViewsRepo<ProductionCount, Long> {

	@Query(value = "select * from v_production_count where brand=?1", nativeQuery = true)
	List<ProductionCount> getProductionCountByBrand(Long id);

	@Query(value = "select * from v_production_count", nativeQuery = true)
	List<ProductionCount> getProductionCount(Long id);

}
