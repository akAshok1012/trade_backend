package com.tm.app.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.LeaveRejectReasonDto;
import com.tm.app.entity.LeaveRejectReason;
import com.tm.app.repo.LeaveRejectReasonRepo;
import com.tm.app.service.LeaveRejectReasonService;

@Service
public class LeaveRejectReasonServiceImpl implements LeaveRejectReasonService {

	@Autowired
	private LeaveRejectReasonRepo leaveRejectReasonRepo;
	
	@Override
	public LeaveRejectReason saveLeaveRejectReason(LeaveRejectReasonDto leaveRejectReasonDto) {
		LeaveRejectReason leaveRejectReason = new LeaveRejectReason();
		BeanUtils.copyProperties(leaveRejectReasonDto, leaveRejectReason);
		LeaveRejectReason leaveRejectReasonDetails = leaveRejectReasonRepo.save(leaveRejectReason);
		return leaveRejectReasonDetails;
	}

	@Override
	public List<LeaveRejectReason> getLeaveRejectReason() {
		return leaveRejectReasonRepo.findAll();
	}

	@Override
	public LeaveRejectReason getLeaveRejectReasonById(Long id) {
		return leaveRejectReasonRepo.getLeaveRejectReasonById(id);
	}

	@Override
	public LeaveRejectReason updateLeaveRejectReason(Long id, LeaveRejectReasonDto leaveRejectReasonDto) {
		LeaveRejectReason leaveRejectReason = leaveRejectReasonRepo.findById(id).orElseThrow();
		BeanUtils.copyProperties(leaveRejectReasonDto, leaveRejectReason);
		return leaveRejectReasonRepo.save(leaveRejectReason);
	}

	@Override
	public void deleteLeaveRejectReasonById(Long id) {
		leaveRejectReasonRepo.deleteById(id);
	}

	@Override
	public Page<LeaveRejectReason> getLeaveRejectReasonListing(DataFilter dataFilter) {
	
		return leaveRejectReasonRepo.findByRejectReasonLikeIgnoreCase(dataFilter.getSearch(),
				PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
						Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}

}
