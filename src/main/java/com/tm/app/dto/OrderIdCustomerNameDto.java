package com.tm.app.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class OrderIdCustomerNameDto {

	private Integer orderId;
	
	private Timestamp dateTime; 
}
