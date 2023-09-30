package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.RejectReasonDto;
import com.tm.app.entity.RejectReason;
import com.tm.app.enums.Rejection;

public interface RejectReasonService {

	RejectReason saveRejectReason(RejectReasonDto rejectReasonDto);

	List<RejectReason> getRejectReason();

	RejectReason getRejectReasonById(Long id);

	RejectReason updateRejectReason(Long id, RejectReasonDto rejectReasonDto);

	void deleteRejectReasonById(Long id);

	Page<RejectReason> getRejectReasonList(DataFilter dataFilter);

	List<RejectReason> getOrderOrLeaveRejection(Rejection rejection);

}
