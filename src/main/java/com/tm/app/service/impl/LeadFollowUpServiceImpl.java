package com.tm.app.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.LeadFollowUpDto;
import com.tm.app.entity.LeadFollowUp;
import com.tm.app.enums.LeadStatus;
import com.tm.app.repo.LeadFollowUpRepo;
import com.tm.app.repo.LeadGenerationRepo;
import com.tm.app.service.LeadFollowUpService;

@Service
public class LeadFollowUpServiceImpl implements LeadFollowUpService {

	@Autowired
	private LeadFollowUpRepo leadFollowUpRepo;

	@Autowired
	private LeadGenerationRepo leadGenerationRepo;

	@Override
	public LeadFollowUp saveLeadFollowUp(LeadFollowUpDto leadFollowUpDto) {
		LeadFollowUp leadFollowUp = new LeadFollowUp();
		BeanUtils.copyProperties(leadFollowUpDto, leadFollowUp);
		leadFollowUp = leadFollowUpRepo.save(leadFollowUp);
		return leadFollowUp;
	}

	@Override
	public List<LeadFollowUp> getLeadFollowUp() {
		return leadFollowUpRepo.findAll();
	}

	@Override
	public LeadFollowUp getLeadFollowUpById(Long id) {
		return leadFollowUpRepo.findById(id).orElseThrow();
	}

	@Override
	public LeadFollowUp updateLeadFollowUp(Long id, LeadFollowUpDto leadFollowUpDto) {
		LeadFollowUp leadFollowUp = leadFollowUpRepo.findById(id).orElseThrow();
		leadFollowUp.setFollowUpDate(leadFollowUpDto.getFollowUpDate());
		leadFollowUp.setStatus(leadFollowUpDto.getStatus());
		leadFollowUp.setNotes(leadFollowUpDto.getNotes());
		leadFollowUp = leadFollowUpRepo.save(leadFollowUp);

		// Update in lead generation table
		leadFollowUp.getLeadGeneration().setFollowupDate(leadFollowUp.getFollowUpDate());
		leadFollowUp.getLeadGeneration().setStatus(leadFollowUp.getStatus());
		leadFollowUp.getLeadGeneration().setNotes(leadFollowUp.getNotes());
		leadFollowUp.getLeadGeneration().setUpdatedBy(leadFollowUp.getUpdatedBy());
		leadGenerationRepo.save(leadFollowUp.getLeadGeneration());

		return leadFollowUp;
	}

	@Override
	public void deleteLeadFollowUpById(Long id) {
		leadFollowUpRepo.deleteById(id);

	}

	@Override
	public Page<LeadFollowUp> getLeadFollowUpList(LeadStatus leadStatus, DataFilter dataFilter) {
		if (leadStatus != null) {
			return leadFollowUpRepo.getLeadFollowUpListByStatus(leadStatus, dataFilter.getSearch(),
					PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
							Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
		} else {
			return leadFollowUpRepo.getLeadFollowUpList(dataFilter.getSearch(), PageRequest.of(dataFilter.getPage(),
					dataFilter.getSize(), Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
		}
	}

}
