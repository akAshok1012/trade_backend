package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.DailyProductionCount;

@Repository
public interface DailyProductionCountRepo extends ReadOnlyViewsRepo<DailyProductionCount, Long> {

	@Query(value = "select * from v_daily_production_count", nativeQuery = true)
	List<DailyProductionCount> getDailyProductionCount();

}
