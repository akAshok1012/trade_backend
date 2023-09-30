package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.LeadGenerationDto;
import com.tm.app.entity.LeadGeneration;

public interface LeadGenerationService {

	LeadGeneration saveLeadGeneration(LeadGenerationDto leadGenerationDto);

	List<LeadGeneration> getLeadGeneration();

	LeadGeneration getLeadGenerationById(Long id);

	LeadGeneration updateLeadGeneration(Long id, LeadGenerationDto leadGenerationDto);

	void deleteLeadGenerationById(Long id);

	Page<LeadGeneration> getLeadGenerationList(DataFilter dataFilter);

}
