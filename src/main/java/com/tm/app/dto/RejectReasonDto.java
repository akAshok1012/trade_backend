package com.tm.app.dto;

import com.tm.app.enums.Rejection;

import lombok.Data;

@Data
public class RejectReasonDto {

	private Long id;
	private String rejectReason;
	private Rejection rejection;
	private String updatedBy;

}
