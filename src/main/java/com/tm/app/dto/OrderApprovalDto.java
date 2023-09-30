package com.tm.app.dto;

import java.util.List;

import com.tm.app.entity.RejectReason;
import com.tm.app.enums.OrderStatus;

import lombok.Data;

@Data
public class OrderApprovalDto {

	private Integer orderId;

	private OrderStatus orderStatus;

	private List<OrderItemDetailsDto> itemDetails;

	private RejectReason rejectReasonId;
	
	private String updatedBy;

}
