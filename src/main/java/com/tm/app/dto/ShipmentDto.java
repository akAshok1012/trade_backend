package com.tm.app.dto;

import java.util.List;

import lombok.Data;

@Data
public class ShipmentDto {
	private Integer salesId;
	private List<ItemAndQuantityDto> itemAndQuantityDto;
	private String updatedBy;
	private String carrier;
	private String trackingNumber;
	private String remark;
}
