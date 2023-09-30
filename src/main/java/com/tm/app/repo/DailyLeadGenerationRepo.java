package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.DailyLeadGeneration;

@Repository
public interface DailyLeadGenerationRepo extends ReadOnlyViewsRepo<DailyLeadGeneration, Long> {

	@Query(value = "select * from v_daily_lead_generation_count", nativeQuery = true)
	List<DailyLeadGeneration> getDailyLeadGeneration();

}
