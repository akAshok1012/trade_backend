package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.ItemUnitOfMeasure;
@Repository
public interface ItemUnitOfMeasureRepo extends JpaRepository<ItemUnitOfMeasure, Long>{

	@Query(value="select * from t_item_unit_of_measure where item_id =:id",nativeQuery = true)
	List<ItemUnitOfMeasure> findByItemMasterId(Long id);

}
