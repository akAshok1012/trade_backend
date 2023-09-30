package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.LeadCountAndCreatedBy;

@Repository
public interface LeadCountAndCreatedByRepo extends ReadOnlyViewsRepo<LeadCountAndCreatedBy, Long> {

	@Query(value = "select * from v_lead_generation_createdBy_count", nativeQuery = true)
	List<LeadCountAndCreatedBy> getLeadGenerationAndCreatedBy();

}
