package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.ProductionCountByBrand;

@Repository
public interface ProductionCountByBrandRepo extends ReadOnlyViewsRepo<ProductionCountByBrand, Long> {

	@Query(value = "select * from v_production_count_by_brand", nativeQuery = true)
	List<ProductionCountByBrand> getProductionCountByBrand();

	@Query(value = "select * from v_production_count_by_brand where brand=?1", nativeQuery = true)
	List<ProductionCountByBrand> getProductionCountByBrand(Long id);

}
