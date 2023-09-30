package com.tm.app.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class ShippedHistoryDto {
	private String carrier;
	private String trackingNumber;
	private Timestamp updatedAt;
	private List<ShippedItemDto> shippedItems;
}