package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.MonthlyProductionCount;

@Repository
public interface MonthlyProductionCountRepo extends ReadOnlyViewsRepo<MonthlyProductionCount, Long> {

//	@Query(value = "select * from v_monthly_production_count where brand=?1", nativeQuery = true)
//	List<MonthlyProductionCount> getMonthlyProductionCountByBrand(Long id);

	@Query(value = "select * from v_monthly_production_count", nativeQuery = true)
	List<MonthlyProductionCount> getMonthlyProductionCount();

}
