package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.WeeklyProductionCount;

@Repository
public interface WeeklyProductionCountRepo extends ReadOnlyViewsRepo<WeeklyProductionCount, Long> {

//	@Query(value = "select * from v_weekly_production_count where brand=?1", nativeQuery = true)
//	List<WeeklyProductionCount> getWeeklyProductionCountByBrand(Long id);

	@Query(value = "select * from v_weekly_production_count", nativeQuery = true)
	List<WeeklyProductionCount> getWeeklyProductionCount();

}
