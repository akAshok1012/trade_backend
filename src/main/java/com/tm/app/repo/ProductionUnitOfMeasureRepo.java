package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.ProductionUnitOfMeasure;

@Repository
public interface ProductionUnitOfMeasureRepo extends JpaRepository<ProductionUnitOfMeasure, Long> {

	@Query(value = "select * from t_production_unit_of_measure where production_id =:id", nativeQuery = true)
	List<ProductionUnitOfMeasure> findByproductionId(Long id);

}
