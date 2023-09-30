package com.tm.app.dto;

import lombok.Data;

@Data
public class SalesInvoiceDto {
	private String salesId;
	private String clientOrganization;
	private String clientName;
	private String clientAddress;
	private String clientEmail;
	private Long clientPhoneNumber;
	private String clientItemName;
	private Integer quantity;
	private Float unitPrice;
	private String description;
	private Long total;
	private String updatedAt;
}