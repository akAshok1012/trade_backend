package com.tm.app.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.RejectReason;
import com.tm.app.enums.Rejection;

@Repository
public interface RejectReasonRepo extends JpaRepository<RejectReason, Long> {

	Page<RejectReason> findByRejectReasonLikeIgnoreCase(String search, PageRequest of);

	List<RejectReason> findByRejection(Rejection rejection);

	boolean existsByRejectReasonEqualsIgnoreCaseAndRejection(String rejectReason, Rejection rejection);

}
