package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.tm.app.entity.LeadCountWithStatus;

public interface LeadCountWithStatusRepo extends ReadOnlyViewsRepo<LeadCountWithStatus, Long> {

	@Query(value = "select * from v_lead_generation_status_count", nativeQuery = true)
	List<LeadCountWithStatus> getLeadGenerationStatusCount();

}
