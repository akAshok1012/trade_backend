package com.tm.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tm.app.entity.LeaveRejectReason;

public interface LeaveRejectReasonRepo extends JpaRepository<LeaveRejectReason, Long> {

	LeaveRejectReason getLeaveRejectReasonById(Long id);

	Page<LeaveRejectReason> findByRejectReasonLikeIgnoreCase(String search, PageRequest of);

}
