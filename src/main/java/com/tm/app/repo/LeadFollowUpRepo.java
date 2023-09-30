package com.tm.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.LeadFollowUp;
import com.tm.app.entity.LeadGeneration;
import com.tm.app.enums.LeadStatus;

@Repository
public interface LeadFollowUpRepo extends JpaRepository<LeadFollowUp, Long> {

	@Query("select lf from LeadFollowUp lf join LeadGeneration lg on (lf.leadGeneration=lg.id) where lower(lg.name) like lower(:search)")
	Page<LeadFollowUp> getLeadFollowUpList(String search, PageRequest of);

	LeadFollowUp findByLeadGeneration(LeadGeneration leadGeneration);

	@Query("select lf from LeadFollowUp lf join LeadGeneration lg on (lf.leadGeneration=lg.id) where lf.status=:leadStatus and lower(lg.name) like lower(:search)")
	Page<LeadFollowUp> getLeadFollowUpListByStatus(LeadStatus leadStatus, String search, PageRequest of);

}
