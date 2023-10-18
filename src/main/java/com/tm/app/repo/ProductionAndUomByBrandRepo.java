package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.ProductionAndUomByBrand;

@Repository
public interface ProductionAndUomByBrandRepo extends ReadOnlyViewsRepo<ProductionAndUomByBrand, Long> {

	@Query(value = "select * from v_production_brand where brand=?1", nativeQuery = true)
	List<ProductionAndUomByBrand> getProductionAndUomByBrand(Long id);

	@Query(value = "select * from v_production_brand", nativeQuery = true)
	List<ProductionAndUomByBrand> getAllProductionAndUomByBrand();

}
