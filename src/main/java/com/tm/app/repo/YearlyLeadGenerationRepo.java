package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.YearlyLeadGeneration;

@Repository
public interface YearlyLeadGenerationRepo extends ReadOnlyViewsRepo<YearlyLeadGeneration, Long> {

	@Query(value = "select * from v_yearly_lead_generation_count", nativeQuery = true)
	List<YearlyLeadGeneration> getYearlyLeadGeneration();

}
