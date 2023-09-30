package com.tm.app.dto;

import java.sql.Timestamp;

import com.tm.app.entity.Customer;

import lombok.Data;

@Data
public class OrderIdDateTimeDto {
	private Integer orderId;

	private Customer customer;

	private Timestamp dateTime;
}
