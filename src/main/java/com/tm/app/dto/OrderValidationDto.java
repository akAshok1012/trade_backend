package com.tm.app.dto;

import lombok.Data;

@Data
public class OrderValidationDto {

	private Long id;
	private Boolean placeOrder;
	private String description;
}
