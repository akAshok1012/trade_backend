package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.LeadFollowUpDto;
import com.tm.app.entity.LeadFollowUp;
import com.tm.app.enums.LeadStatus;

public interface LeadFollowUpService {

	LeadFollowUp saveLeadFollowUp(LeadFollowUpDto leadFollowUpDto);

	List<LeadFollowUp> getLeadFollowUp();

	LeadFollowUp getLeadFollowUpById(Long id);

	LeadFollowUp updateLeadFollowUp(Long id, LeadFollowUpDto leadFollowUpDto);

	void deleteLeadFollowUpById(Long id);

	Page<LeadFollowUp> getLeadFollowUpList(LeadStatus leadStatus, DataFilter dataFilter);

}
