package com.tm.app.dto;

import lombok.Data;

@Data
public class ShippedItemDto {
	private String itemName;
	private Integer orderQuantity;
	private Integer shippedQuantity;
	private Integer balanceQuantity;
	private String unitName;
	private String brandName;
}