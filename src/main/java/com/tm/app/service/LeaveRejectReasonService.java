package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.LeaveRejectReasonDto;
import com.tm.app.entity.LeaveRejectReason;

public interface LeaveRejectReasonService {

	LeaveRejectReason saveLeaveRejectReason(LeaveRejectReasonDto leaveRejectReasonDto);

	List<LeaveRejectReason> getLeaveRejectReason();

	LeaveRejectReason getLeaveRejectReasonById(Long id);

	LeaveRejectReason updateLeaveRejectReason(Long id, LeaveRejectReasonDto leaveRejectReasonDto);

	void deleteLeaveRejectReasonById(Long id);

	Page<LeaveRejectReason> getLeaveRejectReasonListing(DataFilter dataFilter);

	
}
