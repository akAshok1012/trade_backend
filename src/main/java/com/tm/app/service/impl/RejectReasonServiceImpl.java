package com.tm.app.service.impl;

import static com.tm.app.utils.CacheableConstants.REJECT_REASON;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.RejectReasonDto;
import com.tm.app.entity.RejectReason;
import com.tm.app.enums.Rejection;
import com.tm.app.repo.RejectReasonRepo;
import com.tm.app.service.RejectReasonService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class RejectReasonServiceImpl implements RejectReasonService {

	@Autowired
	private RejectReasonRepo rejectReasonRepo;

	@Override
	@CacheEvict(value = REJECT_REASON, allEntries = true)
	public RejectReason saveRejectReason(RejectReasonDto rejectReasonDto) {
		RejectReason rejectReason = new RejectReason();
		try {
			if (rejectReasonRepo.existsByRejectReasonEqualsIgnoreCaseAndRejection(rejectReasonDto.getRejectReason(),
					rejectReasonDto.getRejection())) {
				throw new RuntimeException("Reject Reason already exists!");
			}
			BeanUtils.copyProperties(rejectReasonDto, rejectReason);
			rejectReason = rejectReasonRepo.save(rejectReason);
		} catch (Exception e) {
			log.error("[RejectReason] Adding RejectReason Failed", e);
			throw new RuntimeException(e.getMessage());
		}
		return rejectReason;
	}

	@Override
	@Cacheable(value = REJECT_REASON)
	public List<RejectReason> getRejectReason() {
		log.info("[RejectReason] get RejectReason");
		return (List<RejectReason>) rejectReasonRepo.findAll();
	}

	@Override
	@Cacheable(value = REJECT_REASON, key = "#id")
	public RejectReason getRejectReasonById(Long id) {
		log.info("[RejectReason] Get RejectReasonById");
		return rejectReasonRepo.findById(id).get();
	}

	@Override
	@CacheEvict(value = REJECT_REASON, allEntries = true)
	public RejectReason updateRejectReason(Long id, RejectReasonDto rejectReasonDto) {
		RejectReason rejectReason = rejectReasonRepo.findById(id).orElseThrow();
		try {
			if (rejectReasonRepo.existsByRejectReasonEqualsIgnoreCaseAndRejection(rejectReasonDto.getRejectReason(),
					rejectReasonDto.getRejection())) {
				throw new RuntimeException("Reject Reason already exists!");
			}
			BeanUtils.copyProperties(rejectReasonDto, rejectReason);
			rejectReason = rejectReasonRepo.save(rejectReason);
		} catch (Exception e) {
			log.error("[RejectReason] Updating RejectReason failed", e);
			throw new RuntimeException(e.getMessage());
		}
		return rejectReason;
	}

	@Override
	@CacheEvict(cacheNames = REJECT_REASON, key = "#id", allEntries = true)
	public void deleteRejectReasonById(Long id) {
		log.info("[RejectReason] Deleted RejectReason");
		rejectReasonRepo.deleteById(id);
	}

	@Override
	@Cacheable(value = REJECT_REASON)
	public Page<RejectReason> getRejectReasonList(DataFilter dataFilter) {
		log.info("[RejectReason] Get RejectReasonList");
		return rejectReasonRepo.findByRejectReasonLikeIgnoreCase(dataFilter.getSearch(),
				PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
						Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}

	@Override
	public List<RejectReason> getOrderOrLeaveRejection(Rejection rejection) {
		return rejectReasonRepo.findByRejection(rejection);
	}

}
