package com.tm.app.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.LeadGenerationDto;
import com.tm.app.entity.LeadFollowUp;
import com.tm.app.entity.LeadGeneration;
import com.tm.app.repo.LeadFollowUpRepo;
import com.tm.app.repo.LeadGenerationRepo;
import com.tm.app.service.LeadGenerationService;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class LeadGenerationServiceImpl implements LeadGenerationService {

	@Autowired
	private LeadGenerationRepo leadGenerationRepo;
	
	@Autowired
	private LeadFollowUpRepo leadFollowUpRepo;

	@Override
	public LeadGeneration saveLeadGeneration(LeadGenerationDto leadGenerationDto) {
		LeadGeneration leadGeneration = new LeadGeneration();
		LeadFollowUp leadFollowUp = new LeadFollowUp();
		try {
			if (StringUtils.isNotEmpty(leadGenerationDto.getEmail()) && leadGenerationRepo.existsByEmail(leadGenerationDto.getEmail())) {
				throw new RuntimeException("Email already exists");
			}
			if (Objects.nonNull(leadGenerationDto.getPhone())
					&& leadGenerationRepo.existsByPhone(leadGenerationDto.getPhone())) {
				throw new RuntimeException("Phone number already exists");
			}
			BeanUtils.copyProperties(leadGenerationDto, leadGeneration);
			if (StringUtils.isNotEmpty(leadGenerationDto.getReferenceImageString())) {
				leadGeneration.setReferenceImage(leadGenerationDto.getReferenceImageString().getBytes());
			}
			leadGeneration = leadGenerationRepo.save(leadGeneration);
			// insert into lead followup table
			updateLeadFollowUp(leadGeneration, leadFollowUp);
			
		} catch (Exception e) {
			log.error("[LEADGENERATION] Adding Lead Generation Failed", e);
			throw new RuntimeException(e.getMessage());
		}
		return leadGeneration;
	}

	private void updateLeadFollowUp(LeadGeneration leadGeneration, LeadFollowUp leadFollowUp) {
		leadFollowUp.setLeadGeneration(leadGeneration);
		leadFollowUp.setFollowUpDate(leadGeneration.getFollowupDate());
		leadFollowUp.setStatus(leadGeneration.getStatus());
		leadFollowUp.setUpdatedBy(leadGeneration.getUpdatedBy());
		leadFollowUp=leadFollowUpRepo.save(leadFollowUp);
	}

	@Override
	public List<LeadGeneration> getLeadGeneration() {
		List<LeadGeneration> leadGenerationData = leadGenerationRepo.findAll();
		for (LeadGeneration leadGeneration : leadGenerationData) {
			if (Objects.nonNull(leadGeneration.getReferenceImage())) {
				leadGeneration.setReferenceImageString(new String(leadGeneration.getReferenceImage()));
			}
		}
		return leadGenerationData;
	}

	@Override
	public LeadGeneration getLeadGenerationById(Long id) {
		LeadGeneration leadGeneration = leadGenerationRepo.findById(id).orElseThrow();
		if (Objects.nonNull(leadGeneration.getReferenceImage())) {
			leadGeneration.setReferenceImageString(new String(leadGeneration.getReferenceImage()));
		}
		return leadGeneration;
	}

	@Override
	public LeadGeneration updateLeadGeneration(Long id, LeadGenerationDto leadGenerationDto) {
		LeadGeneration leadGeneration = leadGenerationRepo.findById(id).orElseThrow();
		LeadFollowUp leadFollowUp = leadFollowUpRepo.findByLeadGeneration(leadGeneration);
		try {
			if (StringUtils.isNotEmpty(leadGenerationDto.getEmail()) && leadGenerationRepo.existsByEmail(leadGenerationDto.getEmail())
					&& !leadGeneration.getEmail().equals(leadGenerationDto.getEmail())) {
				throw new RuntimeException("Email already exists");
			}
			if (Objects.nonNull(leadGenerationDto.getPhone())
					&& leadGenerationRepo.existsByPhone(leadGenerationDto.getPhone())
					&& !leadGeneration.getPhone().equals(leadGenerationDto.getPhone())) {
				throw new RuntimeException("PhoneNumber  already exists");
			}
			BeanUtils.copyProperties(leadGenerationDto, leadGeneration);
			if (StringUtils.isNotEmpty(leadGenerationDto.getReferenceImageString())) {
				leadGeneration.setReferenceImage(leadGenerationDto.getReferenceImageString().getBytes());
			}
			leadGeneration = leadGenerationRepo.save(leadGeneration);
			
			updateLeadFollowUp(leadGeneration, leadFollowUp);
			
		} catch (Exception e) {
			log.error("[LEADGENERATION] Updating Lead Generation Failed", e);
			throw new RuntimeException(e.getMessage());
		}
		return leadGeneration;
	}

	@Override
	public void deleteLeadGenerationById(Long id) {
		leadGenerationRepo.deleteById(id);
	}

	@Override
	public Page<LeadGeneration> getLeadGenerationList(DataFilter dataFilter) {
		Page<LeadGeneration> leadGenerationData = leadGenerationRepo.findByNameLikeIgnoreCase(dataFilter.getSearch(),
				PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
						Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
		for (LeadGeneration leadGeneration : leadGenerationData) {
			if (Objects.nonNull(leadGeneration.getReferenceImage())) {
				leadGeneration.setReferenceImageString(new String(leadGeneration.getReferenceImage()));
			}
		}
		return leadGenerationData;
	}
}
