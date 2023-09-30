package com.tm.app.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.LeadGeneration;

@Repository
public interface LeadGenerationRepo extends JpaRepository<LeadGeneration, Long> {

	Page<LeadGeneration> findByNameLikeIgnoreCase(String search, PageRequest of);

	@Query("select tlfu.id,tlg.noOfFollowups,tlg.name,tlg.followupDate from LeadGeneration tlg join LeadFollowUp tlfu on (tlfu.leadGeneration =tlg.id) where tlg.followupDate <= current_date ")
	List<Object[]> getLeadNotification();

	boolean existsByEmail(String email);

	boolean existsByPhone(String phone);

}
