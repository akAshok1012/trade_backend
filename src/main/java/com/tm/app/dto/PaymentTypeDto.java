package com.tm.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTypeDto {

	private Long id;
	private String paymentType;
	private String description;
	private String updatedBy;

}
