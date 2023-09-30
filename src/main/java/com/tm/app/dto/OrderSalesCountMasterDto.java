package com.tm.app.dto;

import lombok.Data;

@Data
public class OrderSalesCountMasterDto {

	private OrderSalesCountDto dailyCount;
	private OrderSalesCountDto monthlyCount;
	private OrderSalesCountDto weeklyCount;
}
