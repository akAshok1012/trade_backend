package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.WeeklyLeadGeneration;

@Repository
public interface WeeklyLeadGenerationRepo extends ReadOnlyViewsRepo<WeeklyLeadGeneration, Long> {

	@Query(value = "select * from v_weekly_lead_generation_count", nativeQuery = true)
	List<WeeklyLeadGeneration> getWeeklyLeadGeneration();

}
