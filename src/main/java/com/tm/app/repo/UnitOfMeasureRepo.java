package com.tm.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.UnitOfMeasure;
@Repository
public interface UnitOfMeasureRepo extends JpaRepository<UnitOfMeasure, Long>{

	Object findByUnitName(String unitName);

	Page<UnitOfMeasure> findByUnitNameLikeIgnoreCase(String search, PageRequest of);

	boolean existsByUnitNameEqualsIgnoreCaseAndUnitWeight(String unitName, Integer unitWeight);

}
